package user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import user.service.UserService;
import user.service.dto.UserDto;

import java.util.Set;

@RestController
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public void addUser(@RequestBody UserDto user) {
        this.userService.addUser(user);
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody UserDto user) {
        this.userService.addUser(user);
    }

    @DeleteMapping("/user/{id}")
    public void removeUser(@PathVariable Long id) {
        this.userService.removeUser(id);
    }

    @GetMapping("/user")
    public Set<UserDto> findAll() {
        return this.userService.findAll();
    }
}
