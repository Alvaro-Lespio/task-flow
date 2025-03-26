package com.example.taskflow.Room;

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

    public Room createRoom(Room roomRequest, User owner) {
        String hashedPassword = passwordEncoder.encode(roomRequest.getPassword());
        Room room = new Room(roomRequest.getName(),hashedPassword,owner);
        return roomRepository.save(room);
    }

    @Override
    public Room getRoomById(Long roomId) {
        return roomRepository.findById(roomId).orElse(null);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room updateRoom(Room room, User user,Long roomModifyId) {
        Room modifyRoom = roomRepository.findById(roomModifyId).orElse(null);
        String hashedPassword = passwordEncoder.encode(room.getPassword());
        if(modifyRoom != null) {
            modifyRoom.setName(room.getName());
            modifyRoom.setPassword(hashedPassword);
            roomRepository.save(modifyRoom);
        }
        return modifyRoom;
    }

    @Override
    public String deleteRoom(Long roomId) {
        String message = "Failed";
        Room deleteRoom = roomRepository.findById(roomId).orElse(null);
        if(deleteRoom != null) {
            roomRepository.delete(deleteRoom);
            message = "Success";
        }
        return message;
    }

    @Override
    public Room addTaskToRoom(Long roomId, User user, TaskRequestDTO taskrequest) {
        Room room = roomRepository.findById(roomId).orElse(null);
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
                if(userAssigned != null) {
                    taskRoom.setAssignedTo(userAssigned);
                }
            } else {
                taskRoom.setAssignedTo(user);
            }
            System.out.println("Tasks antes: " + room.getTasks().size());
            room.addTask(taskRoom);
            System.out.println("Tasks después: " + room.getTasks().size());
            return roomRepository.save(room);
        }
        return null;
    }

    @Override
    public String removeTaskFromRoom(Long roomId, Long taskId) {
        String message = "Failed";
        Room room = roomRepository.findById(roomId).orElse(null);
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
    public Room updateTaskById(Long roomId, User user, TaskRequestDTO taskrequest, Long taskModifyId) {
        Room room = roomRepository.findById(roomId).orElse(null);
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
}
