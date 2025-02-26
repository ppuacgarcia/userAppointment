package com.userAppointment.UserAppointment.permission;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/permission")
public class PermissionController {
    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    public List<Permission> getAllPermissions() {
        return permissionService.getAllPermissions();
    }

    @GetMapping(path = "{permissionId}")
    public ResponseEntity<Object> getPermissionById(@PathVariable("permissionId") UUID permissionId) {
        return permissionService.getPermissionById(permissionId);
    }

    @PostMapping
    public ResponseEntity<Object> createPermission(@Valid @RequestBody PermissionDTO permissionDTO) {
        return permissionService.createPermission(permissionDTO);
    }

    @PutMapping(path = "{permissionId}")
    public ResponseEntity<Object> updatePermission(
            @PathVariable("permissionId") UUID permissionId,
            @Valid @RequestBody PermissionDTO permissionDTO) {
        return permissionService.updatePermission(permissionId, permissionDTO);
    }

    @DeleteMapping(path = "{permissionId}")
    public ResponseEntity<Object> deletePermission(@PathVariable("permissionId") UUID permissionId) {
        return permissionService.deletePermission(permissionId);
    }
}