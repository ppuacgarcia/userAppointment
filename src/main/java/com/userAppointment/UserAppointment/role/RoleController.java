package com.userAppointment.UserAppointment.role;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/role")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping(path = "{roleId}")
    public ResponseEntity<Object> getRoleById(@PathVariable("roleId") UUID roleId) {
        return roleService.getRoleById(roleId);
    }

    @PostMapping
    public ResponseEntity<Object> createRole(@Valid @RequestBody RoleDTO roleDTO) {
        return roleService.createRole(roleDTO);
    }

    @PutMapping(path = "{roleId}")
    public ResponseEntity<Object> updateRole(
            @PathVariable("roleId") UUID roleId,
            @Valid @RequestBody RoleDTO roleDTO) {
        return roleService.updateRole(roleId, roleDTO);
    }

    @DeleteMapping(path = "{roleId}")
    public ResponseEntity<Object> deleteRole(@PathVariable("roleId") UUID roleId) {
        return roleService.deleteRole(roleId);
    }

    @PostMapping(path = "{roleId}/permissions/{permissionId}")
    public ResponseEntity<Object> addPermissionToRole(
            @PathVariable("roleId") UUID roleId,
            @PathVariable("permissionId") UUID permissionId) {
        return roleService.addPermissionToRole(roleId, permissionId);
    }

    @DeleteMapping(path = "{roleId}/permissions/{permissionId}")
    public ResponseEntity<Object> removePermissionFromRole(
            @PathVariable("roleId") UUID roleId,
            @PathVariable("permissionId") UUID permissionId) {
        return roleService.removePermissionFromRole(roleId, permissionId);
    }
}