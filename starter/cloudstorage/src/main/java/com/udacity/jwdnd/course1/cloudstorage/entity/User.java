package com.udacity.jwdnd.course1.cloudstorage.entity;

public class User {
    private Integer userId;
    private String username;
    private String salt;
    private String password;
    private String firstName;
    private String lastName;

    public User(Integer id, String username, String encodedSalt, String hashedPassword, String firstname, String lastname) {
        userId = id;
        this.username = username;
        salt = encodedSalt;
        password = hashedPassword;
        this.firstName = firstname;
        this.lastName = lastname;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", salt='" + salt + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstName + '\'' +
                ", lastname='" + lastName + '\'' +
                '}';
    }
}
