package com.pokemon.backend;

public class Move {

    private String name;
    private String type;
    private int power;
    private double accuracy;
    private boolean physical;

    public Move(String name, String type, int power, double accuracy, boolean physical) {
        this.name = name;
        this.type = type;
        this.power = power;
        this.accuracy = accuracy;
        this.physical = physical;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getPower() {
        return power;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public boolean isPhysical() {
        return physical;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public void setPhysical(boolean physical) {
        this.physical = physical;
    }

}


