package com.group6.cleansweep.models;

public class User {
    public String username, password, cleansweep;

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

    public String getCleansweep() {
        return cleansweep;
    }

    public void setCleansweep(String cleansweep) {
        this.cleansweep = cleansweep;
    }

    public boolean hasCleanSweep(){
        if(cleansweep.isEmpty()){
            return true;
        }
        return false;
    }

    public boolean compareTo(User o){
        if(this.username.equals(o.getUsername())){
            return true;
        }
        return false;
    }
}
