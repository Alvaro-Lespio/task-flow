package com.example.taskflow.Room;

import com.example.taskflow.Task.Task;
import com.example.taskflow.Task.TaskRequestDTO;
import com.example.taskflow.User.IUserRepository;
import com.example.taskflow.User.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface IRoomService {
    Room createRoom(Room room, User user);
    Room getRoomById(Long roomId);
    List<Room> getAllRooms();
    Room updateRoom(Room room, User user,Long roomModifyId);
    String deleteRoom(Long roomId);
    Room addTaskToRoom(Long roomId, User user, TaskRequestDTO taskrequest);
    String removeTaskFromRoom(Long roomId, Long taskId);
    Task getTaskById(Long taskId);
    Room updateTaskById(Long roomId, User user, TaskRequestDTO taskrequest, Long taskModifyId);
}
