package com.userAppointment.UserAppointment.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;

    @Autowired
    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    public ResponseEntity<Object> getPermissionById(UUID id) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<Permission> permissionOptional = permissionRepository.findById(id);
        if (permissionOptional.isEmpty()) {
            data.put("error", true);
            data.put("message", "Permiso no encontrado");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        data.put("data", permissionOptional.get());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<Object> createPermission(PermissionDTO permissionDTO) {
        HashMap<String, Object> data = new HashMap<>();

        // Verificar si ya existe un permiso con ese nombre
        if (permissionRepository.existsByName(permissionDTO.getName())) {
            data.put("error", true);
            data.put("message", "Ya existe un permiso con ese nombre");
            return new ResponseEntity<>(data, HttpStatus.CONFLICT);
        }

        // Crear y guardar el nuevo permiso
        Permission permission = new Permission(permissionDTO.getName());
        Permission savedPermission = permissionRepository.save(permission);

        data.put("data", savedPermission);
        data.put("message", "Permiso creado exitosamente");
        return new ResponseEntity<>(data, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updatePermission(UUID id, PermissionDTO permissionDTO) {
        HashMap<String, Object> data = new HashMap<>();

        // Verificar que el permiso existe
        Optional<Permission> permissionOptional = permissionRepository.findById(id);
        if (permissionOptional.isEmpty()) {
            data.put("error", true);
            data.put("message", "Permiso no encontrado");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }

        Permission permission = permissionOptional.get();

        // Verificar que no exista otro permiso con el mismo nombre
        if (!permission.getName().equals(permissionDTO.getName()) &&
                permissionRepository.existsByName(permissionDTO.getName())) {
            data.put("error", true);
            data.put("message", "Ya existe otro permiso con ese nombre");
            return new ResponseEntity<>(data, HttpStatus.CONFLICT);
        }

        // Actualizar y guardar el permiso
        permission.setName(permissionDTO.getName());
        Permission updatedPermission = permissionRepository.save(permission);

        data.put("data", updatedPermission);
        data.put("message", "Permiso actualizado exitosamente");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<Object> deletePermission(UUID id) {
        HashMap<String, Object> data = new HashMap<>();

        // Verificar que el permiso existe
        if (!permissionRepository.existsById(id)) {
            data.put("error", true);
            data.put("message", "Permiso no encontrado");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }

        // Eliminar el permiso
        permissionRepository.deleteById(id);

        data.put("message", "Permiso eliminado exitosamente");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}