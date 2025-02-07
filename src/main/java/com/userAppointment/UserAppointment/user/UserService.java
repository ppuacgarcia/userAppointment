package com.userAppointment.UserAppointment.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
        return this.userRepository.findAll();
    }

    public void newUser(User user){
        Optional<User> res = userRepository.findByCui(user.getCui());
        if(res.isPresent()){
          throw new IllegalStateException("ya existe el producto");

        }
    }
}
