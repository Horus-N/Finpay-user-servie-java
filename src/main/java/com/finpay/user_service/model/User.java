package com.finpay.user_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(
            nullable = false,
            unique = true
    )
    private String email;
    private String phone;
    private String password;
    private String role;
    public User() {
    }

    public User(String name, String email, String phone, String password,String role) {
        this.name = name;
        this.email = email;
        this.phone = phone;
       this.password=password;
       this.role=role;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}