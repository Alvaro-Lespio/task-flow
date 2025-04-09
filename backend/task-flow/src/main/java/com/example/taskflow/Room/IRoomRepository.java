package com.example.taskflow.Room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface IRoomRepository extends JpaRepository<Room, Long> {
    Room findByRoomCode(String roomCode);

    @Query(value = "SELECT r.* FROM rooms r " +
            "JOIN rooms_users ru ON r.room_code = ru.room_code " +
            "WHERE ru.user_id = :userId", nativeQuery = true)
    List<Room> findRoomsById(@Param("userId") Long id);
}
