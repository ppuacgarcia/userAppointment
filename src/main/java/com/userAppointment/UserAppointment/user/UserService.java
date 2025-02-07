package com.userAppointment.UserAppointment.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public ResponseEntity<Object> newUser(User user){
        Optional<User> res = userRepository.findByCui(user.getCui());
        HashMap<String,Object> data = new HashMap<>() ;

        if(res.isPresent()){
            data.put("Error", true);
            data.put("message","There is already a user with that CUI");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }
        data.put("data", user);
        data.put("message","User created");
        // Guardar el nuevo usuario
        userRepository.save(user);
        return new ResponseEntity<>(
                data,
                HttpStatus.CREATED
        );
    }
    public ResponseEntity<Object> updateUser(UUID userId, User userDetails) {
        HashMap<String,Object> data = new HashMap<>();

        // Buscar usuario por ID
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            data.put("error", true);
            data.put("message", "User not found");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();

        // Verificar si el nuevo CUI ya existe y es diferente al usuario actual
        if (userDetails.getCui() != null && !userDetails.getCui().equals(user.getCui())) {
            Optional<User> userWithNewCui = userRepository.findByCui(userDetails.getCui());
            if (userWithNewCui.isPresent()) {
                data.put("error", true);
                data.put("message", "There is already a user with that CUI");
                return new ResponseEntity<>(data, HttpStatus.CONFLICT);
            }
            user.setCui(userDetails.getCui());
        }

        // Actualizar campos si no son nulos
        if (userDetails.getName() != null) {
            user.setName(userDetails.getName());
        }
        if (userDetails.getTelephone() != null) {
            user.setTelephone(userDetails.getTelephone());
        }
        if (userDetails.getEmail() != null) {
            user.setEmail(userDetails.getEmail());
        }
        if (userDetails.getCreatedAt() != null) {
            user.setCreatedAt(userDetails.getCreatedAt());
        }
        // Para boolean podemos actualizarlo directamente ya que siempre tiene un valor
        user.setStatus(userDetails.isStatus());

        // Guardar usuario actualizado
        User updatedUser = userRepository.save(user);

        data.put("message", "User updated successfully");
        data.put("data", updatedUser);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
