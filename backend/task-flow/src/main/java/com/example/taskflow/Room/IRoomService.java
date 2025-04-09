package com.example.taskflow.Room;

import com.example.taskflow.Task.Task;
import com.example.taskflow.Task.TaskRequestDTO;
import com.example.taskflow.User.IUserRepository;
import com.example.taskflow.User.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface IRoomService {
    Room createRoom(Room room, User user);
    Room getRoomById(String roomId);
    List<Room> getAllRooms(User user);
    Room updateRoom(Room room, User user,String roomModifyId);
    String deleteRoom(String roomId);
    Room addTaskToRoom(String roomId, User user, TaskRequestDTO taskrequest);
    String removeTaskFromRoom(String roomId, Long taskId);
    Task getTaskById(Long taskId);
    Room updateTaskById(String roomId, User user, TaskRequestDTO taskrequest, Long taskModifyId);
    Room joinRoom(String roomCode,String password, User user);
}
