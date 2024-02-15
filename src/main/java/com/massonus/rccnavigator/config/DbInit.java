package com.massonus.rccnavigator.config;

import com.massonus.rccnavigator.entity.User;
import com.massonus.rccnavigator.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DbInit {

    private final UserService userService;

    private Random random = new Random();

    public DbInit(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    private void postConstruct() {

        final User user = new User();
        user.setEmail("user@gmail.com");
        user.setUsername("cat");
        user.setPassword("cat");

        userService.addUser(user, false);

        final User admin = new User();
        admin.setEmail("admin@gmail.com");
        admin.setUsername("admin");
        admin.setPassword("admin");

        userService.addUser(admin, true);
    }
}