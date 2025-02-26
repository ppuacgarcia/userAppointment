package com.userAppointment.UserAppointment.permission;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PermissionDTO {

    @NotBlank(message = "El nombre del permiso no puede estar vacío")
    @Size(max = 50, message = "El nombre del permiso no puede tener más de 50 caracteres")
    private String name;

    // Constructores
    public PermissionDTO() {}

    public PermissionDTO(String name) {
        this.name = name;
    }

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}