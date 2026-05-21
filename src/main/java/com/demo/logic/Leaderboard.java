package com.demo.logic;

import com.demo.GUI.GuiDisplay;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Leaderboard {
    private File file;
    private String filename;
    private GuiDisplay gui;
    private List<String> leaderboard;

    public List<String> getLeaderboard() {
        return leaderboard;
    }

    public Leaderboard(String filename, GuiDisplay gui) {
        this.filename = filename;
        this.file = new File(filename);
        this.gui = gui;
        this.leaderboard = readLeaderboard();
    }

    private List<String> readLeaderboard() {
        List<String> lb = new ArrayList<>();
        try (Scanner sc = new Scanner(filename)) {
            if (file.exists()) {
                while (sc.hasNextLine()) {
                    lb.add(sc.nextLine());
                }
            }
        } catch (Exception e) {
            gui.displayMsg(e.getMessage());
        }
        return lb;
    }

    public void showLeaderboard() {
        try (Scanner sc = new Scanner(filename)) {
            if (file.exists()) {
                while (sc.hasNextLine()) {
                    gui.displayMsg(sc.nextLine());
                }
            }
        } catch (Exception e) {
            gui.displayMsg(e.getMessage());
        }
    }

    public void writeLeaderboard() {
        try (PrintWriter pw = new PrintWriter(filename)) {
            if (file.exists()) {
                // write to file
            }
        } catch (Exception e) {
            gui.displayMsg(e.getMessage());
        }
    }
}
