package com.demo;

import com.demo.GUI.ConsoleGuiDisplay;
import com.demo.GUI.GuiDisplay;
import com.demo.GUI.MainMenu;
import com.demo.logic.GameEngine;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class Main {
    static void main(String[] args) {
        new MainMenu();

        GuiDisplay gui = new ConsoleGuiDisplay();
        Scanner sc = new Scanner(System.in);
        String questionsFile = "qna.txt";
        String leaderboardFile = "leaderboard.txt";

        GameEngine gameEngine = new GameEngine(gui, sc, questionsFile, leaderboardFile);
        gameEngine.mainMenu();

        sc.close();
    }
}

