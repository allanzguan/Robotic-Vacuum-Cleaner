package com.group6.cleansweep.models.roomba;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class CleanSweep {

    private Floor fp;
    private Tile curTile;
    private Tile startTile;
    private Tile lastTile;
    private Logger log;
    private float battery;
    private short dirtBag;
    public boolean isRunning = false;
    public boolean testMode;

    public CleanSweep(Floor f, boolean t){
        fp = f;
        testMode = t;
    }

    public void run() throws InterruptedException, IOException {
        isRunning = true;
        log = Logger.getInstance();
        startTile = TileLocator.findStartingTile(fp); //gets the tile to start at
        curTile = startTile;
        lastTile = startTile;
        boolean run = true;
        battery = 250; //Assumes a full charge at start
        float pathCost = 0;
        dirtBag = 0; //Maxes at 50

        String startMessage = "Starting at Tile: " + curTile.toString();
        System.out.println(startMessage);
        log.write(startMessage);

        while(run){

            Tile North = DemoDecider.Select(fp, getCurrentTile(), lastTile); //calls on a decider to determine next tile to traverse

            //If the decision shows that there are no other valid tiles to go to we end at an emergency shutdown
            if(North == null){
                run = false;
                String deadEnd = "DEAD END INITIATING EMERGENCY SHUTDOWN!";
                System.out.println(deadEnd);
                log.write(deadEnd);
            }
            else {
                //Track Power Consumption
                float powerUse1, powerUse2, powerUseAv;

                //Set powerUse1
                powerUse1 = TileToPower.convert(curTile.getType());

                //Error Handling
                if (powerUse1 == 0) {
                    System.out.println("Invalid Tile Type for Current Tile");
                    log.write("Invalid Tile Type for Current Tile");
                    break;
                }

                //Set powerUse2
                powerUse2 = TileToPower.convert(North.getType());

                //Error Handling
                if (powerUse2 == 0) {
                    System.out.println("Invalid Tile Type for New Tile");
                    log.write("Invalid Tile Type for New Tile");
                    break;
                }

                //Finds Average Power Use
                powerUseAv = (powerUse1 + powerUse2) / 2;

                //Checks if ReturnHome pathfinding is needed
                if (pathCost + (2 * powerUseAv) > battery) {
                    pathCost = ReturnHome.find(fp, curTile);
                    if (pathCost + (2 * powerUseAv) > battery) {
                        this.resupply();
                        if(testMode == true){ //for testing purposes cleansweep will turn off when it comes back to a station
                            run = false;
                        }
                    }
                }

                //Drain Battery
                battery -= powerUseAv;
                pathCost += powerUseAv;
                if (battery <= 0){
                    battery = 0;
                    run = false; //at 0 we end the clean sweep
                }

                lastTile = curTile; //change the previous tile
                curTile = North;

                //Check for Charging Station
                if (curTile.getSpecialty().equals("station")){
                    battery = 250; //Charges
                    if(testMode == true){ //testing mode turns cleansweep off with recharge
                        run = false;
                    }
                }


                String message = "Now on Tile: " + curTile.toString() + " battery: " + battery + "/250.0";
                //if(battery <= 40) message += "\n LOW BATTERY!";
                System.out.println(message);
                log.write(message);

                //Clean Dirt
                while (curTile.getDirt() > 0) {
                    //Checks for pathfinding
                    if (pathCost + powerUse2 > battery) {
                        pathCost = ReturnHome.find(fp, curTile);
                        if (pathCost + powerUse2 > battery) {
                            this.resupply();
                        }
                    }

                    //Clean Dirt
                    curTile.removeDirt();
                    dirtBag++;
                    battery -= powerUse2;

                    message = "Cleaning Tile... battery: " + battery + "/250.0";
                    //if(battery <= 40) message += "\n LOW BATTERY!";
                    System.out.println(message);
                    log.write(message);

                    //increase speed while testing
                    if(testMode == false) {TimeUnit.SECONDS.sleep(1);}

                    //Dirt Bag is Full
                    if (dirtBag == 50) {
                        ReturnHome.find(fp, curTile); //Finds the path of the least cost
                        this.resupply();
                    }
                }

                if(testMode == false) {TimeUnit.SECONDS.sleep(1);}
            }

        }

    }

    public int[] getCurrentTile() {
        /* Only returns coordinates of current tile as int array - no dirt information */
        return new int[]{curTile.getTileCoordinate().getX(), curTile.getTileCoordinate().getY()};
    }

    public Floor getFloor(){
        return fp;
    }

    //Moves CleanSweep to Charging Station
    public void resupply() throws IOException, InterruptedException{
        LinkedList<Node> homePath = ReturnHome.getPath(); //Gets the path to the nearest station
        LinkedList<Node> retrace = new LinkedList<>(); //Holds return route

        Node curTile, nextTile;
        curTile = homePath.removeFirst();

        String message = " Low power. Returning to Charging Station.";
        System.out.println(message);
        log.write(message);

        retrace.add(0, curTile);

        //Returning to Charging Station
        while (!homePath.isEmpty()) {
            nextTile = homePath.removeFirst();
            float powerUse = (TileToPower.convert(curTile.getTile().getType()) + TileToPower.convert(nextTile.getTile().getType())) / 2;
            battery -= powerUse;

            if (!nextTile.getTile().getSpecialty().equals("station")) {
                message = "Now on Tile: " + nextTile.getTile().toString() + " battery: " + battery + "/250.0 \n Low power. Returning to Charging Station.";
                System.out.println(message);
                log.write(message);
            }
            else {
                battery = 250;
                message = "Now on Tile: " + nextTile.getTile().toString() + " Charging... battery: " + battery + "/250.0 \n Returning to Cleaning Cycle.";
                System.out.println(message);
                log.write(message);
                if (dirtBag == 50) {
                    message = "Dirt Capacity Reached. Empty Me.";
                    System.out.println(message);
                    log.write(message);

                    //TO DO: Wait to be emptied.
                    dirtBag = 0;
                }
            }

            curTile = nextTile;
            retrace.add(0, curTile);

            TimeUnit.SECONDS.sleep(1);
        }
        retrace.removeFirst(); //Remove Charging Station from Return Path

        //Retracing Path
        while (!retrace.isEmpty()) {
            nextTile = retrace.removeFirst();
            float powerUse = (TileToPower.convert(curTile.getTile().getType()) + TileToPower.convert(nextTile.getTile().getType())) / 2;
            battery -= powerUse;

            message = "Now on Tile: " + nextTile.getTile().toString() + " battery: " + battery + "/250.0";
            System.out.println(message);
            log.write(message);

            curTile = nextTile;

            TimeUnit.SECONDS.sleep(1);
        }

    }

}
