package com.example.crudrest.service;

import com.example.crudrest.dao.UserDao;
import com.example.crudrest.model.Role;
import com.example.crudrest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @Transactional
//    @Override
//    public boolean add2(User user) {
//        user.setRole(user.getRole());
//        Set<Role> roleSet = new HashSet<>();
//        roleSet = user.getRole();
//        User user1 = new User();
//        user1.setFirstName(user.getFirstName());
//        user1.setLastName(user.getLastName());
//        user1.setAge(user.getAge());
//        user1.setEmail(user.getEmail());
//
//        user1.setRole(roleSet);
//        user1.setPassword(passwordEncoder.encode(user.getPassword()));
//        userDao.add(user1);
//        userDao.add(user);
//        return true;
//    }

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
        if (userDao.getUserById(id).isEnabled()) {
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
