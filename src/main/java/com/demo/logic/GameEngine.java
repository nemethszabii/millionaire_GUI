package com.demo.logic;

import com.demo.GUI.GuiDisplay;
import com.demo.GUI.TerminalHandler;
import com.demo.models.Player;
import com.demo.models.Question;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class GameEngine {
    private GuiDisplay gui;
    private Scanner sc;
    private GameContent gc;
    private Player player;

    public GameEngine(GuiDisplay gui, Scanner sc, String filename) {
        this.gui = gui;
        this.sc = sc;
        this.gc = new GameContent(filename);
        this.player = new Player();
    }

    public void mainMenu() {
        String choice = "";
        do {
            gui.displayMsg("1 - Start Game");
            gui.displayMsg("2 - Leaderboard");
            gui.displayMsg("3 - Quit");
            gui.displayInLineMsg("Choice: ");
            choice = sc.nextLine();
            switch (choice) {
                case "1":
                    startGame();
                    break;
                case "2":
                    showLeaderboard();
                    break;
                case "3":
                    gui.displayMsg("Closing game...");
                    System.exit(0);
                    break;
                default:
                    gui.displayMsg("Choose a correct option [1, 2 or 3]");
                    break;
            }
        } while (true);
    }

    private void startGame() {
        TerminalHandler.clearConsole();
        getPlayerName();
        gui.displayMsg(String.format("Hello, %s! The game is starting in a sec...", player.getName()));
        TreeMap<Integer, Question> qna = this.gc.getQna();
        int i = 0;
        String userAnswer = "";
        while (i < qna.size()) {
            TerminalHandler.clearConsole();
            gc.displayQuestion(gui, i);
            userAnswer = getPlayerAnswer();
            if (userAnswer.equals("a")) {
                gui.displayMsg("Correct!");
                this.player.incrementScore();
                i++;
            } else {
                gui.displayMsg("Wrong!");
            }
        }
    }

    private String getPlayerAnswer() {
        String userAnswer = "";
        do {
            gui.displayInLineMsg("Choose an answer [a, b, c or d]: ");
            userAnswer = sc.nextLine();
        } while (!Set.of("a", "b", "c", "d").contains(userAnswer));
        return userAnswer;
    }

    private void showLeaderboard() {
        gui.displayMsg("Leaderboard");
    }

    private void getPlayerName() {
        String name = "";
        do {
            gui.displayInLineMsg("Name: ");
            name = sc.nextLine();
            if (name.length() < 3) {
                gui.displayMsg("Invalid name. Try again.");
            }
        } while (name.length() < 3);
        this.player.setName(name);
    }
}
