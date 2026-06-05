package com.demo.core.model;

import java.util.HashMap;
import java.util.Map;

public class Player implements Comparable {
    private int rank;
    private String name;
    private int prize;
    private int guaranteedPrize;
    private Map<Integer, Integer> prizes;
    private String[] availableHelps;
    private String formattedElapsedTime;

    public void setFormattedElapsedTime(String elapsedTime) { this.formattedElapsedTime = elapsedTime; }

    public String[] getAvailableHelps() { return availableHelps; }

    public void setAvailableHelps(String[] availableHelps) { this.availableHelps = availableHelps; }

    public int getGuaranteedPrize() {
        return guaranteedPrize;
    }

    public int getRank() { return rank; }
    public String getName() {
        return name;
    }
    public int getPrize() { return prize; }
    public String getTime() { return formattedElapsedTime; }

    public void setName(String name) { this.name = name; }

    public Player() {
        this.prize = 0;
        setUpPrizes();
        availableHelps = new String[] {"50:50", "Phone", "Audience"};
    }

    public Player(String rank, String name, String prize, String formattedElapsedTime) {
        this.rank = Integer.parseInt(rank);
        this.name = name;
        this.prize = Integer.parseInt(prize);
        this.formattedElapsedTime = formattedElapsedTime;
    }

    public void incrementPrize(int index) {
        this.prize = this.prizes.get(index);
        if (index % 5 == 0) {
            this.guaranteedPrize = this.prizes.get(index);
        }
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

    public static int getDataFromFormattedTime(String time, int start, int end) {
        return Integer.parseInt(time.substring(start, end));
    }

    @Override
    public String toString() {
        return String.format("%s (%d) [%s]", this.name, this.prize, this.formattedElapsedTime);
    }

    @Override
    public int compareTo(Object o) {
        int min = Player.getDataFromFormattedTime(this.formattedElapsedTime, 0, 2);
        int sec = Player.getDataFromFormattedTime(this.formattedElapsedTime, 3, 5);
        Player p = (Player) o;
        int pMin = Player.getDataFromFormattedTime(p.formattedElapsedTime, 0, 2);
        int pSec = Player.getDataFromFormattedTime(p.formattedElapsedTime, 3, 5);

        if (min == pMin) {
            if (sec == pSec) {
                return 0;
            } else if (sec < pSec) {
                return -1;
            } else {
                return 1;
            }
        } else if (min > pMin) {
            return 1;
        } else {
            return -1;
        }
    }
}
