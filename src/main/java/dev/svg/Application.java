package dev.svg;

import dev.svg.security.models.Role;
import dev.svg.security.models.User;

import dev.svg.security.services.impl.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            if (userService.getUser("adminbank") == null) {
                userService.saveRole(new Role(null, "ROLE_ADMIN"));
                userService.saveRole(new Role(null, "ROLE_USER"));
                userService.saveUser(new User("adminbank",  "1234", new ArrayList<>()));
                userService.addRoleToUser("adminbank", "ROLE_ADMIN");
            }
        };
    }

}
