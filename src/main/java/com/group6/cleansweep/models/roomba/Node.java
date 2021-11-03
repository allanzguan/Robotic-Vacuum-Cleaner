package com.group6.cleansweep.models.roomba;

public class Node {
    //private variables
    private Node parent;
    private Node child1, child2, child3, child4;
    private Tile tile;
    private float pathCost, totalCost;
    boolean expanded;

    //Constructor
    public Node(Tile tile) {
        this.tile = tile;
        parent = null;
        child1 = null;
        child2 = null;
        child3 = null;
        child4 = null;
        pathCost = 0;
        totalCost = 0;
        expanded = false;
    }

    //Setters
    public void setPathCost(float pc){this.pathCost = pc;}
    public void setTotalCost(float tc){this.totalCost = tc;}
    public void expand(){this.expanded = true;}
    public void addChild(Tile newChild) {
        Node temp = new Node(newChild);
        temp.parent = this;
        temp.setPathCost((TileToPower.convert(this.tile.getType()) + TileToPower.convert(temp.tile.getType())) / 2);
        temp.setTotalCost(this.getTotalCost() + temp.getPathCost());
        if (this.child1 == null)
            this.child1 = temp;
        else if (this.child2 == null)
            this.child2 = temp;
        else if (this.child3 == null)
            this.child3 = temp;
        else if (this.child4 == null)
            this.child4 = temp;
    }

    //Getters
    public Node getParent(){return this.parent;}
    public Node getChild1(){return this.child1;}
    public Node getChild2(){return this.child2;}
    public Node getChild3(){return this.child3;}
    public Node getChild4(){return this.child4;}
    public Tile getTile(){return this.tile;}
    public float getPathCost(){return this.pathCost;}
    public float getTotalCost(){return this.totalCost;}
    public boolean getExpanded(){return this.expanded;}
}
