package com.userAppointment.UserAppointment.user;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.UUID;

import com.userAppointment.UserAppointment.role.Role;

public class UserDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String name;

    @NotNull(message = "El CUI no puede estar vacío")
    @Pattern(regexp = "\\d{13}", message = "El CUI debe tener exactamente 13 dígitos")
    private String cui;

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(max = 16, message = "El nombre de usuario no puede tener más de 16 caracteres")
    private String userName;

    private boolean status;

    @Pattern(regexp = "\\+?[0-9]{7,15}", message = "El teléfono debe ser válido (mínimo 7, máximo 15 dígitos, opcional '+')")
    private String telephone;

    @NotNull(message = "El email no puede estar vacío")
    @Email(message = "El email debe ser válido")
    private String email;

    @NotNull
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotNull(message = "La fecha de nacimiento no puede estar vacía")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate birthDate;

    private UUID roleId;
    private Role role;
    public boolean isValidAge() {
        if (birthDate == null) return false;
        int age = LocalDate.now().getYear() - birthDate.getYear();
        // Ajustar la edad si aún no ha cumplido años este año
        if (birthDate.getMonthValue() > LocalDate.now().getMonthValue() ||
                (birthDate.getMonthValue() == LocalDate.now().getMonthValue() &&
                        birthDate.getDayOfMonth() > LocalDate.now().getDayOfMonth())) {
            age--;
        }
        return age >= 18 && age <= 90;
    }


    // Getters
    public UUID getRoleId() {
        return roleId;
    }
    public String getName() {
        return name;
    }

    public String getCui() {
        return cui;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isStatus() {
        return status;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Role getRole() {
        return role;
    }

    // Setters
    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }


}