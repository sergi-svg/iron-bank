package dev.svg.security.services.interfaces;


import dev.svg.security.models.Role;
import dev.svg.security.models.User;

import java.util.List;

public interface UserServiceInterface {

    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    User getUser(String username);

    List<User> getUsers();
}
