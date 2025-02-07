package com.userAppointment.UserAppointment.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void newUser(@RequestBody User user){
        this.UserService.newUser(user);
    }


}
