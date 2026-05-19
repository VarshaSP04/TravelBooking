package com.travelbooking.model;

import org.bson.types.ObjectId;
import java.util.Date;

public class Admin {
    private ObjectId id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String role; // SUPER_ADMIN, ADMIN
    private Date createdAt;

    public Admin() {
        this.role = "ADMIN";
        this.createdAt = new Date();
    }

    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }
    public String getIdString() { return id != null ? id.toHexString() : null; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
