package com.example.taskflow.Task;

import com.example.taskflow.User.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface ITaskRepository extends JpaRepository<Task,Long> {

    Long id(Long id);
}


