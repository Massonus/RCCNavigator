package com.massonus.rccnavigator.controller;


import com.massonus.rccnavigator.entity.Role;
import com.massonus.rccnavigator.entity.User;
import com.massonus.rccnavigator.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "user/registration";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @PostMapping("/registration")
    public String registrationPost(@RequestParam String username,
                                   @RequestParam String password,
                                   @RequestParam String email) {
        final User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        user.setEmail(email);
        user.setRoles(Collections.singleton(Role.USER));
        userService.saveUser(user, false);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            request.getSession().invalidate();
        }
        return "redirect:/";
    }

}