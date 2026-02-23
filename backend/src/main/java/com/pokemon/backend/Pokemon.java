package com.pokemon.backend;

import java.util.List;

public abstract class Pokemon {

    private final String name;
    private List<String> types;
    private final int level;

    private int currentHp;
    private final int maxHp;

    private int attack;
    private int defense;

    private List<Move> moves;

    private final String food;
    private final String sound;
    private final String owner;
    private final String cry;

    // Constructor (updated)
    public Pokemon(String name, List<String> types, int level, int maxHp, int attack, int defense,
                   String food, String sound, String owner, String cry) {

        this.name = name;
        this.types = types;
        this.level = level;

        this.maxHp = maxHp;
        this.currentHp = maxHp; // Always start at full HP

        this.attack = attack;
        this.defense = defense;

        this.food = food;
        this.sound = sound;
        this.owner = owner;
        this.cry = cry;
    }

    // Getters
    public String getName() {
        return name;
    }

    public List<String> getTypes() {
        return types;
    }

    public String getPrimaryType() {
        return types.getFirst();
    }

    public int getLevel() {
        return level;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public String getFood() {
        return food;
    }

    public String getSound() {
        return sound;
    }

    public String getOwner() {
        return owner;
    }

    public String getCry() {
        return cry;
    }

    // Setters
    public void setTypes(List<String> types) {
        this.types = types;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = Math.max(0, Math.min(currentHp, this.maxHp));
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }
}


