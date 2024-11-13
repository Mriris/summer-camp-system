package org.mua.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true) // 确保身份证号唯一
    private String idNumber;
    @Column(nullable = false)
    private int role = 0;  // 新增字段，默认值为 0

    public User() {}

    public User(String username, String password, String email, String idNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.idNumber = idNumber;
    }

    // Getters and Setters for id, username, password, email, idNumber
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
