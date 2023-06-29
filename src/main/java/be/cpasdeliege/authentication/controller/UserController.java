package be.cpasdeliege.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import be.cpasdeliege.authentication.model.User;
import be.cpasdeliege.authentication.service.UserService;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{name}")
    public List<User> searchUsersByName(@PathVariable String name) {
        return userService.searchUsersByName(name);
    }
}
