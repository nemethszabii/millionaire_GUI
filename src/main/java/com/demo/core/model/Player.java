package com.demo.core.model;

public class Player implements Comparable {
    private String name, formattedElapsedTime, prize, securePrize, nextPrize;
    private int rank;
    private String[] availableHelps;

    public void setFormattedElapsedTime(String elapsedTime) { this.formattedElapsedTime = elapsedTime; }

    public int getRank() { return rank; }

    public String getTime() { return formattedElapsedTime; }

    public String[] getAvailableHelps() { return availableHelps; }
    public void setAvailableHelps(String[] availableHelps) { this.availableHelps = availableHelps; }

    public String getName() {
        return name;
    }
    public void setName(String name) { this.name = name; }

    public String getPrize() { return prize; }
    public void setPrize(String value) { this.prize = value; }

    public String getSecurePrize() {
        return securePrize;
    }
    public void setSecurePrize(String value) { this.securePrize = value; }

    public String getNextPrize() { return nextPrize; }
    public void setNextPrize(String value) { this.nextPrize = value; }

    public Player() {
        this.prize = "0";
        this.securePrize = "0";
        this.nextPrize = "0";
        availableHelps = new String[] {"50:50", "Phone", "Audience"};
    }

    public Player(String rank, String name, String prize, String formattedElapsedTime) {
        this.rank = Integer.parseInt(rank);
        this.name = name;
        this.prize = prize;
        this.formattedElapsedTime = formattedElapsedTime;
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
