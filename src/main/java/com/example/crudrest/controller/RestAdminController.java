package com.example.crudrest.controller;

import com.example.crudrest.model.User;
import com.example.crudrest.service.RoleService;
import com.example.crudrest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/**")
public class RestAdminController {

    private UserService userService;

    private RoleService roleService;

    @Autowired
    public RestAdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/")
    private ResponseEntity<List<User>> listUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByLogin(userName);
        List<User> users = userService.listAllUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping({"/{id}"})
    private ResponseEntity<?> deleteUser(@PathVariable Long id) {
        System.out.println("oppa");
        boolean status = userService.deleteUser(id);
        if (status) {
            return new  ResponseEntity<>(null,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }


    @PutMapping("/")
    private ResponseEntity<User> updateUser(@RequestBody User user) {
        String[] role = getRoleFromResivedUser(user);
        if (user.getPassword().equals("")) {
            user.setPassword((userService.getUserById(user.getId())).getPassword());
        }
        userService.editUser(user, role);
        User responseUser = userService.getUserById(user.getId());
        return ResponseEntity.ok(responseUser);
    }

    @PostMapping({"/"})
    private ResponseEntity<User> createNewUser(@RequestBody User user) {
        String[] role = getRoleFromResivedUser(user);
        userService.add(user, role);
        User responseUser = userService.getUserByLogin(user.getEmail());
        return ResponseEntity.ok(responseUser);
    }

    private String[] getRoleFromResivedUser(User user) {
        String[] role = new String[2];
        if (user.getRole().toString().contains("ROLE_ADMIN")) {
            role[0] = "ROLE_ADMIN";
            if (user.getRole().toString().contains("ROLE_USER")) {
                role[1] = "ROLE_USER";
            }
        } else {
            role[0] = "ROLE_USER";
        }
        return role;
    }
}
