package com.example.crudrest.controller;

import com.example.crudrest.model.Role;
import com.example.crudrest.model.User;
import com.example.crudrest.service.RoleService;
import com.example.crudrest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/rest/**")
public class RestAdminController {

    private UserService userService;

    @Autowired
    public RestAdminController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    RoleService roleService;

//    @RequestMapping("/admin")
//    @ResponseBody
//    private String userList(Model model) {
//        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
//        User user = userService.getUserByLogin(userName);
//        model.addAttribute("adminList", user);
//        model.addAttribute("userList", userService.listAllUsers());
//        model.addAttribute("rolesuser", roleService.getRoles());
//        return "all-users";
//    }

    @GetMapping("/")
    private ResponseEntity<List<User>> listUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByLogin(userName);
        List<User> users = userService.listAllUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping({"/{id}"})
    private boolean deleteUser(@PathVariable("id") Long id, Model model) {
        return userService.deleteUser(id);
//        return "redirect:/admin";
    }


    @PutMapping("/")
    private void updateUser(User user, String[] role) {
        userService.editUser(user, role);
//        return "redirect:/admin";
    }

    @PostMapping({"/"})
    private ResponseEntity<User> createNewUser(@RequestBody User user) {
        String[] role = new String[2];
        if (user.getRole().toString().contains("ROLE_ADMIN")) {
            role[0] = "ROLE_ADMIN";
            if (user.getRole().toString().contains("ROLE_USER")) {
                role[1] = "ROLE_USER";
            }
        } else {
            role[0] = "ROLE_USER";
        }
        userService.add(user, role);
        User responseUser = userService.getUserByLogin(user.getEmail());
        return ResponseEntity.ok(responseUser);
//        return "redirect:/admin";
    }

//    @GetMapping("/error")
//    private String error() {
//        return "error";
//    }
}
