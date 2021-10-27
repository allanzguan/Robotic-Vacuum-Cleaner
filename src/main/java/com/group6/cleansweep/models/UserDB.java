package com.group6.cleansweep.models;

import java.util.ArrayList;


//Using UserDB to mimic user DB.
public class UserDB {

    private static UserDB uc = null;

    public ArrayList<User> ul = new ArrayList<>();

    //Singleton
    public static UserDB getInstance(){
        if (uc == null){
            uc = new UserDB();
        }
        return uc;
    }

    //Check to see if user already exists
    public boolean exists(User u){
        for(User x : ul){
            if(x.compareTo(u)){
                return true;
            }
        }
        return false;
    }

    //Add a user to the list of users
    public void add(User u){
        ul.add(u);
    }

    public User getUser(User u){
        for(User x : ul){
            if(x.compareTo(u)){
                return x;
            }
        }
        return null;
    }
}
