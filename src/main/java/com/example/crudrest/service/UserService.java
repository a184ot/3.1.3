package com.example.crudrest.service;

import com.example.crudrest.model.User;

import java.util.List;

public interface UserService {
    boolean add(User user, String[] roles);
//    boolean add2(User user);
    boolean deleteUser(Long id);
    void editUser(User user, String[] roles);
    List<User> listAllUsers();
    User getUserByLogin(String login);
    User getUserById(Long id);
}
