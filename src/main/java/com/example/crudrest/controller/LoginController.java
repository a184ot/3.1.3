package com.example.crudrest.controller;

import com.example.crudrest.model.User;
import com.example.crudrest.service.UserService;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@Controller
@RequestMapping(name = "/")
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping(name = "/")
    private String Login(){
        return "login";
    }

    @PostConstruct
    private void firstUser() {
        User user = new User("User","Admin", 29,"q@qq.q","123456");
        if (userService.getUserByLogin(user.getEmail())==null) {
            String role[] = new String[]{"ROLE_USER", "ROLE_ADMIN"};
            userService.add(user, role);
        }
    }
}
