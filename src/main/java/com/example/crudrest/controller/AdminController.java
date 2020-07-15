package com.example.crudrest.controller;

import com.example.crudrest.model.User;
import com.example.crudrest.service.RoleService;
import com.example.crudrest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/admin")
    private String userList(Model model) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByLogin(userName);
        model.addAttribute("adminList", user);
        model.addAttribute("userList", userService.listAllUsers());
        model.addAttribute("rolesuser", roleService.getRoles());
        return "all-users";
    }

    @GetMapping("/error")
    private String error() {
        return "error";
    }
}
