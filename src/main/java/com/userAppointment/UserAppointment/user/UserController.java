package com.userAppointment.UserAppointment.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {
    private final UserService UserService;

    @Autowired
    public UserController(UserService UserService){
        this.UserService = UserService;
    }

    @GetMapping
    public List<User> getUser(){
        return UserService.getUsers();
    }

    @PostMapping
    public ResponseEntity<Object> newUser(@RequestBody User user){
        return this.UserService.newUser(user);
    }
    @PutMapping(path = "{userId}")
    public ResponseEntity<Object> updateUser(
            @PathVariable("userId") UUID userId,
            @RequestBody User userDetails) {
        return this.UserService.updateUser(userId, userDetails);
    }


}
