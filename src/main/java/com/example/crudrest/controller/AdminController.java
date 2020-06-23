package com.example.crudrest.controller;

import com.example.crudrest.model.User;
import com.example.crudrest.service.RoleService;
import com.example.crudrest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    RoleService roleService;

    @GetMapping("/admin")
    private String userList(Model model) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByLogin(userName);
        model.addAttribute("adminList", user);
        model.addAttribute("userList", userService.listAllUsers());
        model.addAttribute("rolesuser", roleService.getRoles());
        return "all-users";
    }

    @PostMapping({"delete","admin/delete"})
    private String  deleteUser(@ModelAttribute("id") Long id, Model model) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }


    @PostMapping(value = "/updateUser")
    private String updateUser (User user, String[] role) {
        userService.editUser(user, role);
        return "redirect:/admin";
    }

    @PostMapping({"/createUser", "admin/createUser"})
    private String createNewUser(User user, String[] role) {
        userService.add(user,role);
        return "redirect:/admin";
    }

    @GetMapping("/error")
    private String error() {
        return "error";
    }
}
