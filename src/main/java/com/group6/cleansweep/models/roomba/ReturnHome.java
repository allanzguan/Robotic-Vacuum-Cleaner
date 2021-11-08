package com.group6.cleansweep.models.roomba;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

//Return Home AI
public class ReturnHome {
    private static final LinkedList<Node> solution = new LinkedList<>(); //Holds most efficient path
    private static final LinkedList<Node> explore = new LinkedList<>(); //Holds a list of explored nodes

    //Find the most efficient path to a charging station
    public static float find(Floor f, Tile curTile) {
        solution.clear();
        explore.clear();
        Node head = new Node(curTile);
        LinkedList<Node> queue = new LinkedList<>(); //Holds Expandable Nodes
        queue.add(0, head);

        //Run while there exists nodes to expand
        while(!queue.isEmpty()) {
            Node ex = queue.removeFirst();
            explore.add(ex);

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
            if (!test.getType().equals("wall") && !test.getType().equals("stairs") && !test.getSpecialty().contains("Closed Door") && notExplored(test)) {
                ex.addChild(test);
            }
            //Test West
            test = TileLocator.findTile(f, ex.getTile().getTileCoordinate().getX() - 1, ex.getTile().getTileCoordinate().getY());
            if (!test.getType().equals("wall") && !test.getType().equals("stairs") && !test.getSpecialty().contains("Closed Door") && notExplored(test)) {
                ex.addChild(test);
            }
            //Test East
            test = TileLocator.findTile(f, ex.getTile().getTileCoordinate().getX() + 1, ex.getTile().getTileCoordinate().getY());
            if (!test.getType().equals("wall") && !test.getType().equals("stairs") && !test.getSpecialty().contains("Closed Door") && notExplored(test)) {
                ex.addChild(test);
            }
            //Test South
            test = TileLocator.findTile(f, ex.getTile().getTileCoordinate().getX(), ex.getTile().getTileCoordinate().getY() - 1);
            if (!test.getType().equals("wall") && !test.getType().equals("stairs") && !test.getSpecialty().contains("Closed Door") && notExplored(test)) {
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

    //Get Solution
    public static LinkedList<Node> getPath() {return solution;}

    //Checks if a tile has already been explored
    public static boolean notExplored(Tile test) {
        int x = test.getTileCoordinate().getX();
        int y = test.getTileCoordinate().getY();

        //Checks explore for tile coordinates
        for (int i = 0; i < explore.size(); i++) {
            Node temp = explore.get(i);
            if (x == temp.getTile().getTileCoordinate().getX() && y == temp.getTile().getTileCoordinate().getY())
                return false;
        }

        //If not found
        return true;
    }
}
