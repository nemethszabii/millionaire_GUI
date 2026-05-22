package com.demo.logic;

import com.demo.GUI.GuiDisplay;
import com.demo.GUI.TerminalHandler;
import com.demo.models.Player;
import com.demo.models.Question;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class GameEngine {
    private final GuiDisplay gui;
    private final Scanner sc;
    private final GameContent gc;
    private final Player player;
    private String gameState;

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
                    runGame();
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

    private void runGame() {
        TerminalHandler.clearConsole();
        getPlayerName();
        inGameLogic();
    }

    private void inGameLogic() {
        TreeMap<Integer, Question> qna = this.gc.getQna();
        int i = 0;
        String userAnswer = "";
        while (i < qna.size()) {
            this.gameState = "play";
            TerminalHandler.clearConsole();
            gc.displayQuestion(gui, i, player);
            userAnswer = getPlayerAnswer();
            if (!userAnswer.equals("q")) {
                if (gc.getShuffledOptions().get(userAnswer).equals(gc.getQna().get(i).getAnswer())) {
                    gui.displayMsg("Correct!");
                    i++;
                    this.player.incrementPrize(i);
                    this.gameState = "play";
                } else {
                    i = qna.size();
                    this.gameState = "lost";
                }
            } else {
                i = qna.size();
                this.gameState = "quit";
            }
        }
        handleEndOfGame();
    }

    private void handleEndOfGame() {
        switch (this.gameState) {
            case "play":
                gui.displayMsg("Congrats! You win the millionaire game!");
                gui.displayMsg("Your prize is: $" + player.getPrize());
                break;
            case "quit":
                gui.displayMsg("You have chosen to quit the game!");
                gui.displayMsg("Your prize is: $" + player.getGuaranteedPrize());
                break;
            case "lost":
                gui.displayMsg("You have answered incorrectly. The game is over!");
                gui.displayMsg("Your prize is: $" + player.getGuaranteedPrize());
                break;
            default:
                break;
        }
    }

    private String getPlayerAnswer() {
        String answer;
        do {
            gui.displayInLineMsg("Choose an answer [a, b, c or d] or quit [q]: ");
            answer = sc.nextLine();
        } while (!Set.of("a", "b", "c", "d", "q").contains(answer));
        return answer;
    }

    private void showLeaderboard() {
        gui.displayMsg("Leaderboard");
    }

    private void getPlayerName() {
        displayRules();
        String name;
        do {
            gui.displayInLineMsg("Name: ");
            name = sc.nextLine();
            if (name.length() < 3) {
                gui.displayMsg("Invalid name. Try again.");
            }
        } while (name.length() < 3);
        this.player.setName(name);
        gui.displayMsg(String.format("Hello, %s! The game is starting in a sec...", player.getName()));
    }

    private void displayRules() {
        gui.displayMsg("""
                Game Rules!
                - If answer is wrong, you lose!
                - If answer is correct, you gain score and move forward to the next question!
                - You get a guaranteed prize after the 5., 10. and 15. question!
                - Your result will be written to the leaderboard!
        """);
    }
}
