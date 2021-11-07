package com.group6.cleansweep.models.roomba;

public class Tile {

    //To determine the location of the tile
    private TileCoordinate tc;

    //To determine the type of tile
    private String type;

    //How much dirt is on the tile
    private int dirt;
    private String specialty;

    public Tile(TileCoordinate tc, String t, int d, String sp){
        this.tc = tc;
        type = t;
        dirt = d;
        specialty = sp;
    }

    public TileCoordinate getTileCoordinate() {return tc;}
    public String getType() {return type;}
    public int getDirt() {return dirt;}
    public String getSpecialty() {return specialty;}

    //Idea on removing dirt to not expose too much info on variables
    //this can change if needed
    public void removeDirt(){
        dirt = dirt - 1;
    }

    //sends an overall summary of the Tile
    @Override
    public String toString(){
        return "xCoord: " + tc.getX() + " yCoord: " + tc.getY() + " type: " + type + " dirt amount: " + dirt;
    }
}
