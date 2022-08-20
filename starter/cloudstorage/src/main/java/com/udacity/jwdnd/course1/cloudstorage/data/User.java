package com.udacity.jwdnd.course1.cloudstorage.data;

public class User {
    private Integer userId;
    private String username;
    private String salt;
    private String password;
    private String firstname;
    private String lastname;

    public User(Integer id, String username, String encodedSalt, String hashedPassword, String firstname, String lastname) {
        userId = id;
        this.username = username;
        salt = encodedSalt;
        password = hashedPassword;
        this.firstname = firstname;
        this.lastname = lastname;
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
