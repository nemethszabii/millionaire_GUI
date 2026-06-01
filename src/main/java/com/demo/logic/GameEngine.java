package com.demo.logic;

import com.demo.GUI.GuiDisplay;
import com.demo.GUI.TerminalHandler;
import com.demo.models.Player;
import com.demo.models.Question;

import java.sql.Time;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class GameEngine {
    private final Set<String> BASE_ANSWER_OPTIONS = Set.of("a", "b", "c", "d", "h", "q");
    private final GuiDisplay gui;
    private final Scanner sc;
    private final GameContent gc;
    private Player player;
    private String gameState;
    private Help help;
    private Leaderboard leaderboard;
    private Long startingTime;
    private Long endTime;

    public GameEngine(GuiDisplay gui, Scanner sc, String questionsFile, String leaderboardFile) {
        this.gui = gui;
        this.sc = sc;
        this.gc = new GameContent(questionsFile);
        this.leaderboard = new Leaderboard(leaderboardFile, gui);
    }

    public void mainMenuConsole() {
        String choice = "";
        do {
            gui.displayMsg("1 - Start Game");
            gui.displayMsg("2 - Leaderboard");
            gui.displayMsg("3 - Quit");
            gui.displayInLineMsg("Choice: ");
            choice = sc.nextLine();
            switch (choice) {
                case "1":
                    preGameSetUps();
                    break;
                case "2":
                    leaderboard.show();
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

    private void preGameSetUps() {
        TerminalHandler.clearConsole();
        this.player = new Player();
        this.help = new Help(gui, sc, player);
        getPlayerName();
        inGameLogic();
    }

    private void inGameLogic() {
        TreeMap<Integer, Question> qna = this.gc.getQna();
        int i = 0;
        String userAnswer = "";
        this.startingTime = System.nanoTime();
        while (i < qna.size()) {
            boolean isCorrect = false;
            TreeMap<String, String> shuffledOptions = this.gc.getShuffledOptions();
            this.gameState = "play";
            TerminalHandler.clearConsole();
            gc.displayQuestion(gui, i, player);
            userAnswer = getPlayerAnswer(BASE_ANSWER_OPTIONS);
            switch (userAnswer) {
                case "q":
                    i = qna.size();
                    gameState = "quit";
                    break;
                case "h":
                    if (player.getAvailableHelps().length > 0) {
                        String solutionKey = gc.getKeyFromValue(i);
                        userAnswer = help.helpMenu(shuffledOptions, solutionKey);
                    } else {
                        gui.displayMsg("No available help found! You have already used up all of them.");
                        userAnswer = getPlayerAnswer(Set.of("a", "b", "c", "d", "q"), true);
                    }
                    isCorrect = evaluatePlayerAnswer(shuffledOptions, userAnswer, i);
                    break;
                default:
                    isCorrect = evaluatePlayerAnswer(shuffledOptions, userAnswer, i);
                    break;
            }

            if  (isCorrect) {
                i++;
                this.player.incrementPrize(i);
            } else {
                i = qna.size();
            }
        }
        this.endTime = System.nanoTime();
        handleEndOfGame();
    }

    private boolean evaluatePlayerAnswer(TreeMap<String, String> shuffledOptions, String userAnswer, int i) {
        if (userAnswer.equals("q")) {
            gameState = "quit";
            return false;
        } else {
            String chosenAnswer = shuffledOptions.get(userAnswer);
            String realAnswer = gc.getQna().get(i).getAnswer();
            if (chosenAnswer.equals(realAnswer)) {
                gui.displayMsg("Correct!");
                this.gameState = "play";
                return true;
            } else {
                this.gameState = "lost";
                return false;
            }
        }
    }

    private void handleEndOfGame() {
        switch (this.gameState) {
            case "play":
                gui.displayMsg("Congrats! You win the millionaire game!");
                gui.displayMsg("Your prize is: $" + player.getPrize());

                String elapsedTime = formatElapsedTime();
                this.player.setFormattedElapsedTime(elapsedTime);
                this.leaderboard.addPlayerToLeaderboard(this.player);

                this.leaderboard.write();
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

    private String getPlayerAnswer(Set<String> answerOptions) {
        String answer;
        do {
            gui.displayInLineMsg("Choose an answer [a, b, c, d] or get help [h] or to quit [q]: ");
            answer = sc.nextLine();
        } while (!answerOptions.contains(answer));
        return answer;
    }

    private String getPlayerAnswer(Set<String> answerOptions, boolean comingFromHelp) {
        String answer;
        String msg = comingFromHelp ? "Please enter your choice [a, b, c, d] or quit [q]: " : "Choose an answer [a, b, c, d] or get help [h] or to quit [q]: ";
        do {
            gui.displayInLineMsg(msg);
            answer = sc.nextLine();
        } while (!answerOptions.contains(answer));
        return answer;
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
                - Your name will be written to the leaderboard with the play time, but only if you win!
        """);
    }

    private String formatElapsedTime() {
        int elapsedSec = (int)Math.floor(((this.endTime - this.startingTime) / Math.pow(10, 9)) % 60);
        int elapsedMin = (int)Math.floor((((this.endTime - this.startingTime) / Math.pow(10, 9)) / 60) % 60);
        return (elapsedMin > 10 ? String.valueOf(elapsedMin) : "0" + elapsedMin) + ":" + (elapsedSec > 10 ? String.valueOf(elapsedSec) : "0" + elapsedSec);
    }
}
