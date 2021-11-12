package com.group6.cleansweep.models;

import java.io.*;
import java.util.ArrayList;


//Using UserDB to mimic user DB.
public class UserDB {

    private static UserDB uc = null;

    public ArrayList<User> ul = new ArrayList<>();

    public static String file = "src/main/java/com/group6/cleansweep/models/database.txt";

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
//        try{
//            BufferedReader br = new BufferedReader(new FileReader(file));
//            String st;
//            while((st = br.readLine()) != null){
//                String[] un = st.split(" ");
//                if(un[0].equals(u.getUsername())){
//                    User newGuy = new User(u.getUsername(), u.getPassword());
//                    ul.add(newGuy);
//                    if(un.length == 3){
//                        newGuy.setCleansweep(un[2]);
//                    }
//                    return true;
//                }
//            }
//        }catch(Exception e){e.printStackTrace();}

        return false;
    }

    //Add a user to the list of users
    public void add(User u){
        ul.add(u);
    }

    public void addToDB(User u){
        ul.add(u);
        try{
            String entry = "\n" + u.getUsername() + " " + u.getPassword();
            Writer add = new BufferedWriter(new FileWriter(file, true));
            add.append(entry);
            add.close();
        }catch(Exception e){e.printStackTrace();}
    }

    public User getUser(User u){
        for(User x : ul){
            if(x.compareTo(u)){
                return x;
            }
        }
        return null;
    }

    public User getByHash(int h){
        for(User x : ul){
            if(x.getHash() == h){
                return x;
            }
        }
        return null;
    }
}
