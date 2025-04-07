package com.example.taskflow.Room;

import com.example.taskflow.Task.Task;
import com.example.taskflow.Task.TaskRequestDTO;
import com.example.taskflow.User.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room")
public class RoomController {

    private IRoomService roomService;
    public RoomController(IRoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody Room room, @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(roomService.createRoom(room,user), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable Long id) {
        return new ResponseEntity<>(roomService.getRoomById(id), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> getAllRooms() {
        return new ResponseEntity<>(roomService.getAllRooms(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRoom(@RequestBody Room room, @AuthenticationPrincipal User user,@PathVariable Long id) {
        return new ResponseEntity<>(roomService.updateRoom(room,user,id), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> updateRoom(@PathVariable Long id) {
        return new ResponseEntity<>(roomService.deleteRoom(id),HttpStatus.OK);
    }

    @PostMapping("/task/{id}")
    public ResponseEntity<?> addTaskToRoom(@PathVariable Long id, @AuthenticationPrincipal User user,@RequestBody TaskRequestDTO task) {
        return new ResponseEntity<>(roomService.addTaskToRoom(id,user,task),HttpStatus.OK);
    }

    @DeleteMapping("/task/{id}/{idTask}")
    public ResponseEntity<?> removeTaskFromRoom(@PathVariable Long id,@PathVariable Long idTask) {
        return new ResponseEntity<>(roomService.removeTaskFromRoom(id,idTask),HttpStatus.OK);
    }
    @GetMapping("/task/{id}")
    public ResponseEntity<?> getTasksByIdFromRoom(@PathVariable Long id) {
        return new ResponseEntity<>(roomService.getTaskById(id),HttpStatus.OK);
    }
    @PutMapping("/task/{id}/{idTask}")
    public ResponseEntity<?> updateTaskToRoom(@PathVariable Long id, @AuthenticationPrincipal User user,@RequestBody TaskRequestDTO task,@PathVariable Long idTask) {
        return new ResponseEntity<>(roomService.updateTaskById(id,user,task,idTask),HttpStatus.OK);
    }

}
