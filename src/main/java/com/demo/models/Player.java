package com.demo.models;

public class Player {
    private String name;
    private byte score;

    public byte getScore() {
        return score;
    }

    public void setScore(byte score) {
        this.score = score;
    }

    public void incrementScore() {
        this.score += 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public Player() {
    }

    @Override
    public String toString() {
        return String.format("%s: [%d]", name, score);
    }
}
