package com.userAppointment.UserAppointment.user;

import com.userAppointment.UserAppointment.role.Role;
import com.userAppointment.UserAppointment.role.RoleRepository;
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
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> getUsers(){
        return this.userRepository.findAll();
    }

    public ResponseEntity<Object> newUser(UserDTO userDTO){
        HashMap<String, Object> data = new HashMap<>();
        // Validar la edad
        if (!userDTO.isValidAge()) {
            data.put("error", true);
            data.put("message", "El usuario debe tener entre 18 y 90 años");
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }

        // Verificar CUI único
        Optional<User> resCui = userRepository.findByCui(userDTO.getCui());
        if(resCui.isPresent()){
            data.put("error", true);
            data.put("message", "Ya existe un usuario con ese CUI");
            return new ResponseEntity<>(data, HttpStatus.CONFLICT);
        }

        // Verificar userName único
        Optional<User> resUserName = userRepository.findByUserName(userDTO.getUserName());
        if(resUserName.isPresent()){
            data.put("error", true);
            data.put("message", "Ya existe un usuario con ese nombre de usuario");
            return new ResponseEntity<>(data, HttpStatus.CONFLICT);
        }
        Role role = null;
        if (userDTO.getRoleId() != null) {
            Optional<Role> roleOptional = roleRepository.findById(userDTO.getRoleId());
            if (roleOptional.isEmpty()) {
                data.put("error", true);
                data.put("message", "Rol no encontrado");
                return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
            }
            role = roleOptional.get();
        }
        // Crear un objeto User a partir del DTO
        User newUser = new User();
        newUser.setName(userDTO.getName());
        newUser.setCui(userDTO.getCui());
        newUser.setUserName(userDTO.getUserName());
        newUser.setStatus(userDTO.isStatus());
        newUser.setTelephone(userDTO.getTelephone());
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(userDTO.getPassword());
        newUser.setBirthDate(userDTO.getBirthDate());
        newUser.setRole(role);
        // Asegurar que status está inicializado (aunque ya se hace en el constructor)
        newUser.setStatus(userDTO.isStatus());

        // Hasheado de contraseña (deberías implementar esto)
        // newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Guardar el nuevo usuario
        User savedUser = userRepository.save(newUser);

        data.put("data", savedUser);
        data.put("message", "Usuario creado exitosamente");
        return new ResponseEntity<>(data, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateUser(UUID userId, UserDTO userDetails) {
        HashMap<String, Object> data = new HashMap<>();

        // Buscar usuario por ID
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            data.put("error", true);
            data.put("message", "Usuario no encontrado");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();

        // Verificar si el nuevo CUI ya existe y es diferente al usuario actual
        if (userDetails.getCui() != null && !userDetails.getCui().equals(user.getCui())) {
            Optional<User> userWithNewCui = userRepository.findByCui(userDetails.getCui());
            if (userWithNewCui.isPresent()) {
                data.put("error", true);
                data.put("message", "Ya existe un usuario con ese CUI");
                return new ResponseEntity<>(data, HttpStatus.CONFLICT);
            }
            user.setCui(userDetails.getCui());
        }
        // Actualizar campos si no son nulos
        // buscar rol si existe
        Optional<Role>savedRole = Optional.empty();
        if(userDetails.getRole()!=null){
            savedRole = roleRepository.findById(userDetails.getRoleId());
            savedRole.ifPresent(user::setRole);
        }
        if (userDetails.getName() != null) {
            user.setName(userDetails.getName());
        }
        if (userDetails.getUserName() != null) {
            user.setUserName(userDetails.getUserName());
        }
        if (userDetails.getTelephone() != null) {
            user.setTelephone(userDetails.getTelephone());
        }
        if (userDetails.getEmail() != null) {
            user.setEmail(userDetails.getEmail());
        }
        if (userDetails.getPassword() != null) {
            user.setPassword(userDetails.getPassword());
        }
        if (userDetails.getBirthDate() != null) {
            user.setBirthDate(userDetails.getBirthDate());
        }



        // Para boolean podemos actualizarlo directamente ya que siempre tiene un valor
        user.setStatus(userDetails.isStatus());

        // Guardar usuario actualizado
        User updatedUser = userRepository.save(user);

        data.put("message", "Usuario actualizado exitosamente");
        data.put("data", updatedUser);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}