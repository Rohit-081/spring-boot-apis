package com.example.learning.microservices.microservices.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Encrypted;

@Data
@Document
public class User {
    @Id
    private String userId;
    @Indexed(unique=true)
    private String username;
    @Indexed(unique=true)
    private String emailId;
    @Encrypted
    private String password;
    private String mobileNumber;
    private String userRole;
    private String firstName;
    private String lastName;

    public User(String userId, String username, String emailId, String password, String mobileNumber,
                String userRole, String firstName, String lastName){
        this.username = username;
        this.userId = userId;
        this.emailId = emailId;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.userRole = userRole;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User() {
        return;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmailId() {
        return emailId;
    }
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getMobileNumber() {
        return mobileNumber;
    }
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public String getUserRole() {
        return userRole;
    }
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
