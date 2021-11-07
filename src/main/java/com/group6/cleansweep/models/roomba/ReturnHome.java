package com.group6.cleansweep.models.roomba;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

//Return Home AI
public class ReturnHome {
    private static final LinkedList<Node> solution = new LinkedList<>();

    //Find the most efficient path to a charging station
    public static float find(Floor f, Tile curTile) {
        solution.clear();
        Node head = new Node(curTile);
        LinkedList<Node> queue = new LinkedList<>(); //Holds Expandable Nodes
        queue.add(0, head);

        //Run while there exists nodes to expand
        while(!queue.isEmpty()) {
            Node ex = queue.removeFirst();
            ex.expand();

            //Found a Solution
            if (ex.getTile().getSpecialty().equals("station")) {
                float tc = ex.getTotalCost();
                solution.add(0, ex);
                while (ex.getParent() != null) {
                    ex = ex.getParent();
                    solution.add(0, ex);
                }
                return tc;
            }

            //Find which directions the Clean Sweep can move
            //Test North
            Tile test = TileLocator.findTile(f, ex.getTile().getTileCoordinate().getX(),ex.getTile().getTileCoordinate().getY() + 1);
            if (!test.getType().equals("wall") && !test.getType().equals("stairs") && !test.getSpecialty().contains("Closed Door")/*&& not explored*/) {
                ex.addChild(test);
            }
            //Test West
            test = TileLocator.findTile(f, ex.getTile().getTileCoordinate().getX() - 1, ex.getTile().getTileCoordinate().getY());
            if (!test.getType().equals("wall") && !test.getType().equals("stairs") && !test.getSpecialty().contains("Closed Door")/*&& not explored*/) {
                ex.addChild(test);
            }
            //Test East
            test = TileLocator.findTile(f, ex.getTile().getTileCoordinate().getX() + 1, ex.getTile().getTileCoordinate().getY());
            if (!test.getType().equals("wall") && !test.getType().equals("stairs") && !test.getSpecialty().contains("Closed Door")/*&& not explored*/) {
                ex.addChild(test);
            }
            //Test South
            test = TileLocator.findTile(f, ex.getTile().getTileCoordinate().getX(), ex.getTile().getTileCoordinate().getY() - 1);
            if (!test.getType().equals("wall") && !test.getType().equals("stairs") && !test.getSpecialty().contains("Closed Door")/*&& not explored*/) {
                ex.addChild(test);
            }

            //Add to queue
            if (ex.getChild1() != null) {
                queue.add(0, ex.getChild1());
                if (ex.getChild2() != null) {
                    queue.add(0, ex.getChild2());
                    if (ex.getChild3() != null) {
                        queue.add(0, ex.getChild3());
                        if (ex.getChild4() != null)
                            queue.add(0, ex.getChild4());
                    }
                }
            }

            //Sort queue by totalCost
            queue.sort((a, b) -> a.getTotalCost() < b.getTotalCost() ? -1 : a.getTotalCost() == b.getTotalCost()? 0 : 1);
        }
        return -1; //Should Not Happen

    }

    //Moves CleanSweep to Charging Station
    public static float execute(Logger log, float battery) throws IOException, InterruptedException{
        Node curTile, nextTile;
        curTile = solution.removeFirst();
        LinkedList<Node> retrace = new LinkedList<>(); //Holds return route

        String message = " Low power. Returning to Charging Station.";
        System.out.println(message);
        log.write(message);

        retrace.add(0, curTile);

        //Returning to Charging Station
        while (!solution.isEmpty()) {
            nextTile = solution.removeFirst();
            float powerUse = (TileToPower.convert(curTile.getTile().getType()) + TileToPower.convert(nextTile.getTile().getType())) / 2;
            battery -= powerUse;

            if (!nextTile.getTile().getSpecialty().equals("station")) {
                message = "Now on Tile: " + nextTile.getTile().toString() + " battery: " + battery + "/250.0 \n Low power. Returning to Charging Station.";
            }
            else {
                battery = 250;
                message = "Now on Tile: " + nextTile.getTile().toString() + " Charging... battery: " + battery + "/250.0 \n Returning to Cleaning Cycle.";
            }
            System.out.println(message);
            log.write(message);

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

        return battery;
    }
}
