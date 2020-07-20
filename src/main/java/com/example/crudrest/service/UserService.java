package com.example.crudrest.service;

import com.example.crudrest.model.User;

import java.util.List;

public interface UserService {
    void add(User user);
    boolean deleteUser(Long id);
    void editUser(User user);
    List<User> listAllUsers();
    User getUserByLogin(String login);
    User getUserById(Long id);
}
