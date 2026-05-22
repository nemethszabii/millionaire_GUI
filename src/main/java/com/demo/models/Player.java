package com.demo.models;

import java.util.HashMap;
import java.util.Map;

public class Player {
    private String name;
    private int prize;
    private int guaranteedPrize;
    private Map<Integer, Integer> prizes;

    public int getGuaranteedPrize() {
        return guaranteedPrize;
    }

    public int getPrize() {
        return prize;
    }

    public void incrementPrize(int index) {
        this.prize = this.prizes.get(index);
        if (index % 5 == 0) {
            this.guaranteedPrize = this.prizes.get(index);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player(String name) {
        this.name = name;
        this.prize = 0;
        setUpPrizes();
    }

    public Player() {
        this.prize = 0;
        setUpPrizes();
    }

    private void setUpPrizes() {
        this.prizes = new HashMap<>();
        this.prizes.put(1, 100);
        this.prizes.put(2, 200);
        this.prizes.put(3, 300);
        this.prizes.put(4, 500);
        this.prizes.put(5, 1000);
        this.prizes.put(6, 2000);
        this.prizes.put(7, 4000);
        this.prizes.put(8, 8000);
        this.prizes.put(9, 16000);
        this.prizes.put(10, 32000);
        this.prizes.put(11, 64000);
        this.prizes.put(12, 125000);
        this.prizes.put(13, 250000);
        this.prizes.put(14, 500000);
        this.prizes.put(15, 1000000);
    }

    @Override
    public String toString() {
        return String.format("$%s: [%d]", name, prize);
    }
}
