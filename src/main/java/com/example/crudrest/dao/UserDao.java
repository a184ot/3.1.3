package com.example.crudrest.dao;

import com.example.crudrest.model.User;

import java.util.List;

public interface UserDao {
    void add(User user);
    void deleteUser(Long id);
    void editUser(User user);
    List<User> listAllUsers();
    User getUserByLogin(String email);
    User getUserById(Long id);
    User findByUsername(String username);
}
