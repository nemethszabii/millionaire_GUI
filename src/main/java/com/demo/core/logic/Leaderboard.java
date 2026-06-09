package com.demo.core.logic;

import com.demo.core.model.Player;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class Leaderboard {
    private File file;
    private String filename;
    private List<Player> leaderboard;

    public Leaderboard(String filename) {
        this.filename = filename;
        this.file = new File(filename);
        this.leaderboard = read();
    }

    public List<Player> getLeaderboard() { return this.leaderboard; }

    public void addPlayerToLeaderboard(Player player) { this.leaderboard.add(player); }

    private List<Player> read() {
        List<Player> players = new ArrayList<>();
        try (Scanner sc = new Scanner(file)) {
            if (file.exists()) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] split = line.split(" ");
                    Player p = new Player(
                            String.valueOf(split[0].charAt(0)),
                            split[1],
                            split[2].substring(1, split[2].length() - 1),
                            split[3].substring(1, split[3].length() - 1)
                    );
                    players.add(p);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return players;
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
            System.out.println(e.getMessage());
        }
    }
}
