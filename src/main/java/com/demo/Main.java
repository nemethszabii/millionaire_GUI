package com.demo;

import com.demo.GUI.ConsoleGuiDisplay;
import com.demo.GUI.GuiDisplay;
import com.demo.logic.GameEngine;

import java.util.Scanner;

public class Main {
    static void main(String[] args) {
        GuiDisplay gui = new ConsoleGuiDisplay();
        Scanner sc = new Scanner(System.in);
        String filename = "qna.txt";

        GameEngine gameEngine = new GameEngine(gui, sc, filename);
        gameEngine.mainMenu();

        sc.close();
    }
}

