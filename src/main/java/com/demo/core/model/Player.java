package com.demo.core.model;

public class Player implements Comparable {
    private String name, formattedElapsedTime, prize, securePrize, nextPrize;
    private int rank;
    private long startTime, elapsedTime;

    public int getRank() { return this.rank; }

    public String getTime() { return this.formattedElapsedTime; }

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
        this.prize = "$0";
        this.securePrize = "$0";
        this.nextPrize = "$0";
    }

    public Player(String rank, String name, String prize, String formattedElapsedTime) {
        this.rank = Integer.parseInt(rank);
        this.name = name;
        this.prize = prize;
        this.formattedElapsedTime = formattedElapsedTime;
    }

    public void startTimer() { this.startTime = System.currentTimeMillis(); }

    public void stopTimer() {
        this.elapsedTime = System.currentTimeMillis() - this.startTime;
        createFormattedElapsedTime();
    }

    private void createFormattedElapsedTime() {
        int sec = (int) Math.floor((double) this.elapsedTime / 1000) % 60;
        int min = (int) Math.floor(((double) (this.elapsedTime / 1000) / 60) % 60);

        String secString = sec < 10 ? "0" + sec : String.valueOf(sec);
        String minString = min < 10 ? "0" + min : String.valueOf(min);
        this.formattedElapsedTime = minString + ":" + secString;
    }

    public static int getDataFromFormattedTime(String time, int start, int end) {
        return Integer.parseInt(time.substring(start, end));
    }

    @Override
    public String toString() {
        return String.format("%s (%s) [%s]", this.name, this.prize, this.formattedElapsedTime);
    }

    @Override
    public int compareTo(Object o) {
        int min = Player.getDataFromFormattedTime(this.formattedElapsedTime, 0, 2);
        int sec = Player.getDataFromFormattedTime(this.formattedElapsedTime, 3, 5);
        Player p = (Player) o;
        int pMin = Player.getDataFromFormattedTime(p.formattedElapsedTime, 0, 2);
        int pSec = Player.getDataFromFormattedTime(p.formattedElapsedTime, 3, 5);

        if (min == pMin) {
            return Integer.compare(sec, pSec);
        } else if (min > pMin) {
            return 1;
        } else {
            return -1;
        }
    }
}
