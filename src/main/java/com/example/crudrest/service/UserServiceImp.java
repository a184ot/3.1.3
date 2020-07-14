package com.example.crudrest.service;

import com.example.crudrest.dao.UserDao;
import com.example.crudrest.model.Role;
import com.example.crudrest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImp implements UserService {

    private UserDao userDao;

    @Autowired
    public UserServiceImp(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    private RoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public boolean add(User user, String[] roles) {
        Set<Role> roleSet = new HashSet<>();
        for (String role : roles) {
            Role roleDB = roleService.getRoleByName(role);
            roleSet.add(roleDB);
        }
        user.setRole(roleSet);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.add(user);
        return true;
    }


    @Transactional
    @Override
    public void editUser(User user, String[] roles) {
        Set<Role> roleSet = new HashSet<>();
        Role roleDB;
        for (String role : roles) {
            roleDB = roleService.getRoleByName(role);
            roleSet.add(roleDB);
        }
        user.setRole(roleSet);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.editUser(user);
    }

    @Transactional
    @Override
    public boolean deleteUser(Long id) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        if (userDao.getUserById(id).isEnabled() && !(userDao.getUserById(id).getUsername()).equals(userName)) {
            userDao.deleteUser(id);
            return true;
        }
        return false;
    }

    @Override
    public List<User> listAllUsers() {
        return userDao.listAllUsers();
    }

    @Override
    public User getUserByLogin(String login) {
        return userDao.getUserByLogin(login);
    }

    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }
}
