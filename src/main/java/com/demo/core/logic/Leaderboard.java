package com.demo.core.logic;

import com.demo.console.GuiDisplay;
import com.demo.core.model.Player;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class Leaderboard {
    private File file;
    private String filename;
    private GuiDisplay gui;
    private List<Player> leaderboard;

    public Leaderboard(String filename) {
        this.filename = filename;
        this.file = new File(filename);
        this.leaderboard = read();
    }

    public void addPlayerToLeaderboard(Player player) { this.leaderboard.add(player); }

    private List<Player> read() {
        List<Player> players = new ArrayList<>();
        try (Scanner sc = new Scanner(file)) {
            if (file.exists()) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] split = line.split(" ");
                    Player p = new Player(split[1], split[2].substring(1, split[2].length() - 1));
                    players.add(p);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return players;
    }

    public void show() {
        gui.displayMsg("\nLEADERBOARD");
        try (Scanner sc = new Scanner(file)) {
            if (file.exists()) {
                while (sc.hasNextLine()) {
                    gui.displayMsg(sc.nextLine());
                }
            }
        } catch (Exception e) {
            gui.displayMsg(e.getMessage());
        }
        gui.displayMsg("");
    }

    public void write() {
        Collections.sort(leaderboard);
        try (FileWriter fw = new FileWriter(filename)) {
            if (file.exists()) {
                for (int i = 0; i < leaderboard.size(); i++) {
                    fw.write(i + 1 + ". " + leaderboard.get(i).toString() + "\n");
                }
            }
        } catch (Exception e) {
            gui.displayMsg(e.getMessage());
        }
    }
}
