package com.example.crudrest.controller;

import com.example.crudrest.model.User;
import com.example.crudrest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    private UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    private String listUsers(ModelMap model) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByLogin(userName);
        model.addAttribute("userList", user);
        return "userPage";
    }
}
