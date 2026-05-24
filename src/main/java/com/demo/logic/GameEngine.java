package com.demo.logic;

import com.demo.GUI.GuiDisplay;
import com.demo.GUI.TerminalHandler;
import com.demo.models.Player;
import com.demo.models.Question;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class GameEngine {
    private final Set<String> BASE_ANSWER_OPTIONS = Set.of("a", "b", "c", "d", "h", "q");
    private final GuiDisplay gui;
    private final Scanner sc;
    private final GameContent gc;
    private final Player player;
    private String gameState;
    private Help help;

    public GameEngine(GuiDisplay gui, Scanner sc, String filename) {
        this.gui = gui;
        this.sc = sc;
        this.gc = new GameContent(filename);
        this.player = new Player();
        this.help = new Help(gui, sc);
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
            boolean isCorrect = false;
            TreeMap<String, String> shuffledOptions = this.gc.getShuffledOptions();
            this.gameState = "play";
            TerminalHandler.clearConsole();
            gc.displayQuestion(gui, i, player);
            userAnswer = getPlayerAnswer(BASE_ANSWER_OPTIONS);
            switch (userAnswer) {
                case "q":
                    i = qna.size();
                    this.gameState = "quit";
                    break;
                case "h":
                    userAnswer = mainHelpLogic(shuffledOptions, i);
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
        handleEndOfGame();
    }

    private boolean evaluatePlayerAnswer(TreeMap<String, String> shuffledOptions, String userAnswer, int i) {
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

    private String getPlayerAnswer(Set<String> answerOptions) {
        String answer;
        do {
            if (answerOptions.size() > 2) {
                gui.displayInLineMsg("Choose an answer [a, b, c, d] or get help [h] or to quit [q]: ");
            } else {
                gui.displayInLineMsg("Choose an option: ");
            }
            answer = sc.nextLine();
        } while (!answerOptions.contains(answer));
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

    public String mainHelpLogic(TreeMap<String, String> shuffledOptions, int i) {
        String answer = this.help.displayHelpOptions();
        switch (answer) {
            case "1":
                String solutionKey = this.gc.getKeyFromValue(i);
                Set<String> optionsFiftyFifty = this.help.fiftyFifty(shuffledOptions, solutionKey);
                return getPlayerAnswer(optionsFiftyFifty);
            case "2":
                this.help.phone(shuffledOptions);
                break;
            case "3":
                this.help.audience(shuffledOptions);
                break;
            default:
                gui.displayMsg("Choose a correct option!");
                return "";
        }
        return "";
    }
}
