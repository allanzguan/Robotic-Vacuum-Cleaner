package com.group6.cleansweep.models;

public class User {
    public String username, password;

    public User(){}

    public User(String u, String p){
        this.username = u;
        this.password = p;

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
}
