package com.group6.cleansweep.models;

import com.group6.cleansweep.models.roomba.CleanSweep;

import java.io.IOException;

public class User {
    public String username, password;
    public CleanSweep cleansweep;

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

    public CleanSweep getCleansweep() {
        return cleansweep;
    }

    public void setCleansweep(CleanSweep cleansweep) {
        this.cleansweep = cleansweep;
    }

//    public boolean hasCleanSweep(){
//        if(cleansweep == null){
//            return true;
//        }
//        return false;
//    }

    public boolean compareTo(User o){
        if(this.username.equals(o.getUsername())){
            return true;
        }
        return false;
    }

    public String hasRegister(){
        if (cleansweep != null){
            return "YES";
        }
        return "NO";
    }

    public String cleansweepRunning(){
        if(cleansweep.isRunning == true){
            return "YES";
        }
        return "NO";
    }

    public String onFloor(){
        return cleansweep.getFloor().getFloorName();
    }

    Thread thread = new Thread(){
        public void run() {
            try {
                cleansweep.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };


    public void start(){
        if(cleansweep.isRunning == false){
            thread.start();
        }
        else{
            ;
        }
    }

    public String getLocation(){
        int[] coordinates = cleansweep.getCurrentTile();
        String xCoord = "X: " + coordinates[0];
        String yCoord = " Y: " + coordinates[1];
        String location = xCoord + yCoord;
        return location;
    }

    public int getDirtTotal(){
        return cleansweep.getDirtCleaned();
    }

    public int getDirtbag() {return cleansweep.getDirtbag();}

    public String getBattery(){
        if(cleansweep == null){
            return "--";
        }
        return String.valueOf(Math.round((cleansweep.getBattery()/250)*100* 100.0) / 100.0)+"%";
    }

    public int getHash(){
        return this.hashCode();
    }
}
