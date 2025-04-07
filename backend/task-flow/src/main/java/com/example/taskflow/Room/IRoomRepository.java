package com.example.taskflow.Room;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoomRepository extends JpaRepository<Room, Long> {
    Room findByRoomCode(String roomCode);
}
