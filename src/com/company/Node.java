package com.company;

public class Node {
    private double x;
    private double y;
    private int nodeId;
    private int n0;
    private boolean bc;


    public Node(double x, double y, int id, boolean bc) {
        this.x = x;
        this.y = y;
        this.bc = bc;
        nodeId = id;


    }

    private void calculateH(){

    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getNodeId() {
        return nodeId;
    }

    public boolean isBc() {
        return bc;
    }
}
