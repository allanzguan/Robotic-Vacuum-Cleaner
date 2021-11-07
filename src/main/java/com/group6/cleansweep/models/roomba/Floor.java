package com.group6.cleansweep.models.roomba;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;


public class Floor {

    //We use a linked list to store all the rooms in a floor
    private LinkedList<Room> roomList = new LinkedList<Room>();
    private String floorName;
    private static final Pattern ext = Pattern.compile("(?<=.)\\.[^.]+$");


    public Floor(String fileName){
        floorName = ext.matcher(new File(fileName).getName()).replaceAll("");
        //when we are given a file name, we try using GSON to read the JSON file
        try {

            Type fl = new TypeToken<LinkedList<Room>>(){}.getType();

            Reader js = new FileReader(fileName);

            Gson gson = new Gson();

            //This method will read the JSON content and fill out all the info for Rooms and Tiles
            roomList = gson.fromJson(js, fl);

            //For loop to verify that all the tiles have been read.
            for(Room r : roomList){
                System.out.println(r);
            }
        }catch (IOException io){
            io.printStackTrace();
        }
    }

    //return the room list
    public List<Room> getRoomList() {
        return roomList;
    }

    public String getFloorName(){
        return floorName;
    }
}
