package com.userAppointment.UserAppointment.user;
import com.userAppointment.UserAppointment.role.Role;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "\"app_user\"")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(unique = true, nullable = false)
    private String cui;

    @Column(unique = true, nullable = false, name ="user_name")
    private String userName;

    private boolean status;

    private String telephone;
    private String email;
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    private LocalDate birthDate;


    public User() {
    }

    public User(UserDTO dto) {
        this.name = dto.getName();
        this.cui = dto.getCui();
        this.userName = dto.getUserName();
        this.status = dto.isStatus();
        this.telephone = dto.getTelephone();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.birthDate = dto.getBirthDate();
        this.role = dto.getRole();  // Añadir el rol desde el DTO
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
