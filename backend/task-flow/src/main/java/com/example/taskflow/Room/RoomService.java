package com.example.taskflow.Room;

import com.example.taskflow.Exception.RoomCreateFailedException;
import com.example.taskflow.Exception.RoomUpdateFailedException;
import com.example.taskflow.Exception.TaskCreateFailedException;
import com.example.taskflow.Exception.TaskUpdateFailedException;
import com.example.taskflow.Task.ITaskRepository;
import com.example.taskflow.Task.Task;
import com.example.taskflow.Task.TaskRequestDTO;
import com.example.taskflow.User.IUserRepository;
import com.example.taskflow.User.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService implements IRoomService {
    private final PasswordEncoder passwordEncoder;
    private IRoomRepository roomRepository;
    private IUserRepository userRepository;
    private ITaskRepository taskRepository;
    public RoomService(IRoomRepository roomRepository, PasswordEncoder passwordEncoder, IUserRepository userRepository, ITaskRepository taskRepository) {
        this.roomRepository = roomRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public Room createRoom(RoomDTO roomRequest, User owner) {
        if(roomRequest == null){
            throw new RoomCreateFailedException("Room request is null");
        }else if(roomRequest.getName() == null || roomRequest.getPassword() == null){
            throw new RoomCreateFailedException("Room name or password is null");
        }
        String hashedPassword = passwordEncoder.encode(roomRequest.getPassword());
        Room room = new Room(roomRequest.getName(),hashedPassword,owner);
        return roomRepository.save(room);
    }

    @Override
    public Room getRoomById(String roomId) {
        return roomRepository.findByRoomCode(roomId);
    }

    @Override
    public List<Room> getAllRooms(User user) {
        Long id  = user.getId();
        return roomRepository.findRoomsById(id);
    }

    @Override
    public Room updateRoom(RoomDTO room, User user,String roomModifyId) {
        if(room == null){
            throw new RoomUpdateFailedException("room is null");
        }
        Room modifyRoom = roomRepository.findByRoomCode(roomModifyId);
        if(modifyRoom != null) {
            if(room.getPassword()!=null) {
                String hashedPassword = passwordEncoder.encode(room.getPassword());
                modifyRoom.setPassword(hashedPassword);
            }
            modifyRoom.setName(room.getName());
            roomRepository.save(modifyRoom);
        }
        return modifyRoom;
    }

    @Override
    public String deleteRoom(String roomId) {
        String message = "Failed";
        Room deleteRoom = roomRepository.findByRoomCode(roomId);
        if(deleteRoom != null) {
            roomRepository.delete(deleteRoom);
            message = "Success";
        }
        return message;
    }

    @Override
    public Room addTaskToRoom(String roomId, User user, TaskRequestDTO taskrequest) {
        Room room = roomRepository.findByRoomCode(roomId);
        if(taskrequest == null) {
            throw new TaskCreateFailedException("Task request is null");
        }else if(taskrequest.getDescription() == null || taskrequest.getDifficulty() == null || taskrequest.getFinishDate() == null || taskrequest.getTitle() == null) {
            throw new TaskCreateFailedException("Task request values are null");
        }
        if(room != null) {
            Task taskRoom = new Task();
            taskRoom.setDescription(taskrequest.getDescription());
            taskRoom.setFinishDate(taskrequest.getFinishDate());
            taskRoom.setTitle(taskrequest.getTitle());
            taskRoom.setDifficulty(taskrequest.getDifficulty());
            taskRoom.setAssignedBy(user);
            taskRoom.setRoom(room);
            String assignedToUsername = taskrequest.getAssignedTo();
            if (assignedToUsername != null && !assignedToUsername.isEmpty()) {
                User userAssigned = userRepository.findByUsername(assignedToUsername).orElse(null);
                if (userAssigned == null) {
                    throw new IllegalArgumentException("User not found: " + assignedToUsername);
                }
                if (!room.getUsers().contains(userAssigned)) {
                    throw new IllegalArgumentException("Hey, " + assignedToUsername + " isn't joined in the room!");
                }
                taskRoom.setAssignedTo(userAssigned);

            } else {
                taskRoom.setAssignedTo(user);
            }
            room.addTask(taskRoom);
            return roomRepository.save(room);
        }
        return null;
    }

    @Override
    public String removeTaskFromRoom(String roomId, Long taskId) {
        String message = "Failed";
        Room room = roomRepository.findByRoomCode(roomId);
        if(room != null) {
            taskRepository.deleteById(taskId);
            message = "Success";
        }
       return message;
    }

    @Override
    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }


    @Override
    public Room updateTaskById(String roomId, User user, TaskRequestDTO taskrequest, Long taskModifyId) {
        if(taskrequest == null) {
            throw new TaskUpdateFailedException("Task request is null");
        }else if(taskrequest.getDescription() == null || taskrequest.getDifficulty() == null || taskrequest.getFinishDate() == null || taskrequest.getTitle() == null) {
            throw new TaskUpdateFailedException("Task request values are null");
        }
        Room room = roomRepository.findByRoomCode(roomId);
        if(room != null) {
            Task task = taskRepository.findById(taskModifyId).orElse(null);
            if(task != null) {
                task.setDescription(taskrequest.getDescription());
                task.setFinishDate(taskrequest.getFinishDate());
                task.setTitle(taskrequest.getTitle());
                task.setDifficulty(taskrequest.getDifficulty());
                task.setAssignedBy(user);
                task.setRoom(room);
                String assignedToUsername = taskrequest.getAssignedTo();
                if (assignedToUsername != null && !assignedToUsername.isEmpty()) {
                    User userAssigned = userRepository.findByUsername(assignedToUsername).orElse(null);
                    if(userAssigned != null) {
                        task.setAssignedTo(userAssigned);
                    }
                } else {
                    task.setAssignedTo(user);
                }
            }

            room.addTask(task);
            return roomRepository.save(room);
        }
        return null;
    }

    @Override
    public Room joinRoom(String roomCode, String password, User user) {
        Room room = roomRepository.findByRoomCode(roomCode);
        if(room != null) {
            if(room.getUsers().contains(user)) {
                throw new RuntimeException("User already joined");
            }
            String storedHashedPassword = room.getPassword();
            if(storedHashedPassword != null && !storedHashedPassword.isEmpty()) {
                if(password == null || password.isEmpty()) {
                    throw new RuntimeException("Password is null or empty");
                }
                if(!passwordEncoder.matches(password, storedHashedPassword)) {
                    throw new RuntimeException("Wrong password");
                }
            }
            room.addUser(user);
        }
        return roomRepository.save(room);
    }
}
