package com.example.taskflow.User;

import java.util.List;

public interface IUserService {
    List<User> findAll();
    User save(User user);
}
