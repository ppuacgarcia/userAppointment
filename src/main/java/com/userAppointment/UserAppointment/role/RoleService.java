package com.userAppointment.UserAppointment.role;

import com.userAppointment.UserAppointment.permission.Permission;
import com.userAppointment.UserAppointment.permission.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public ResponseEntity<Object> getRoleById(UUID id) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (roleOptional.isEmpty()) {
            data.put("error", true);
            data.put("message", "Rol no encontrado");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        data.put("data", roleOptional.get());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Object> createRole(RoleDTO roleDTO) {
        HashMap<String, Object> data = new HashMap<>();

        // Verificar si ya existe un rol con ese nombre
        if (roleRepository.existsByName(roleDTO.getName())) {
            data.put("error", true);
            data.put("message", "Ya existe un rol con ese nombre");
            return new ResponseEntity<>(data, HttpStatus.CONFLICT);
        }

        // Crear el nuevo rol
        Role role = new Role(roleDTO.getName());

        // Asignar permisos si se han proporcionado
        if (roleDTO.getPermissionIds() != null && !roleDTO.getPermissionIds().isEmpty()) {
            Set<Permission> permissions = new HashSet<>();

            for (UUID permissionId : roleDTO.getPermissionIds()) {
                Optional<Permission> permissionOpt = permissionRepository.findById(permissionId);
                if (permissionOpt.isEmpty()) {
                    data.put("error", true);
                    data.put("message", "Permiso con ID " + permissionId + " no encontrado");
                    return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
                }
                permissions.add(permissionOpt.get());
            }

            role.setPermissions(permissions);
        }

        // Guardar el rol
        Role savedRole = roleRepository.save(role);

        data.put("data", savedRole);
        data.put("message", "Rol creado exitosamente");
        return new ResponseEntity<>(data, HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<Object> updateRole(UUID id, RoleDTO roleDTO) {
        HashMap<String, Object> data = new HashMap<>();

        // Verificar que el rol existe
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (roleOptional.isEmpty()) {
            data.put("error", true);
            data.put("message", "Rol no encontrado");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }

        Role role = roleOptional.get();

        // Verificar que no exista otro rol con el mismo nombre
        if (!role.getName().equals(roleDTO.getName()) &&
                roleRepository.existsByName(roleDTO.getName())) {
            data.put("error", true);
            data.put("message", "Ya existe otro rol con ese nombre");
            return new ResponseEntity<>(data, HttpStatus.CONFLICT);
        }

        // Actualizar el nombre del rol
        role.setName(roleDTO.getName());

        // Actualizar permisos si se han proporcionado
        if (roleDTO.getPermissionIds() != null) {
            Set<Permission> permissions = new HashSet<>();

            for (UUID permissionId : roleDTO.getPermissionIds()) {
                Optional<Permission> permissionOpt = permissionRepository.findById(permissionId);
                if (permissionOpt.isEmpty()) {
                    data.put("error", true);
                    data.put("message", "Permiso con ID " + permissionId + " no encontrado");
                    return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
                }
                permissions.add(permissionOpt.get());
            }

            role.setPermissions(permissions);
        }

        // Guardar el rol actualizado
        Role updatedRole = roleRepository.save(role);

        data.put("data", updatedRole);
        data.put("message", "Rol actualizado exitosamente");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteRole(UUID id) {
        HashMap<String, Object> data = new HashMap<>();

        // Verificar que el rol existe
        if (!roleRepository.existsById(id)) {
            data.put("error", true);
            data.put("message", "Rol no encontrado");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }

        // Eliminar el rol
        roleRepository.deleteById(id);

        data.put("message", "Rol eliminado exitosamente");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Object> addPermissionToRole(UUID roleId, UUID permissionId) {
        HashMap<String, Object> data = new HashMap<>();

        // Verificar que el rol existe
        Optional<Role> roleOptional = roleRepository.findById(roleId);
        if (roleOptional.isEmpty()) {
            data.put("error", true);
            data.put("message", "Rol no encontrado");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }

        // Verificar que el permiso existe
        Optional<Permission> permissionOptional = permissionRepository.findById(permissionId);
        if (permissionOptional.isEmpty()) {
            data.put("error", true);
            data.put("message", "Permiso no encontrado");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }

        Role role = roleOptional.get();
        Permission permission = permissionOptional.get();

        // Agregar el permiso al rol
        role.addPermission(permission);
        roleRepository.save(role);

        data.put("message", "Permiso agregado al rol exitosamente");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Object> removePermissionFromRole(UUID roleId, UUID permissionId) {
        HashMap<String, Object> data = new HashMap<>();

        // Verificar que el rol existe
        Optional<Role> roleOptional = roleRepository.findById(roleId);
        if (roleOptional.isEmpty()) {
            data.put("error", true);
            data.put("message", "Rol no encontrado");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }

        // Verificar que el permiso existe
        Optional<Permission> permissionOptional = permissionRepository.findById(permissionId);
        if (permissionOptional.isEmpty()) {
            data.put("error", true);
            data.put("message", "Permiso no encontrado");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }

        Role role = roleOptional.get();
        Permission permission = permissionOptional.get();

        // Remover el permiso del rol
        role.getPermissions().remove(permission);
        roleRepository.save(role);

        data.put("message", "Permiso removido del rol exitosamente");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}