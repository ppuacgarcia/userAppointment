package com.userAppointment.UserAppointment.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;
import java.util.UUID;

public class RoleDTO {

    @NotBlank(message = "El nombre del rol no puede estar vacío")
    @Size(max = 50, message = "El nombre del rol no puede tener más de 50 caracteres")
    private String name;

    // IDs de los permisos asociados a este rol
    private Set<UUID> permissionIds;

    // Constructores
    public RoleDTO() {}

    public RoleDTO(String name) {
        this.name = name;
    }

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UUID> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(Set<UUID> permissionIds) {
        this.permissionIds = permissionIds;
    }
}