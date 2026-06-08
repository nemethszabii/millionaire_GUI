package com.demo.core.logic;

import com.demo.console.GuiDisplay;
import com.demo.core.model.Player;
import com.demo.core.model.Question;

import java.io.File;
import java.util.Scanner;
import java.util.TreeMap;

public class GameEngine {
    //private final GuiDisplay gui;
    private final GameContent gameContent;
    private Player player;
    private String gameState;
    private Help help;
    private Leaderboard leaderboard;
    private Long startingTime;
    private Long endTime;
    private int questionCounter;

    public GameEngine() {
        this.questionCounter = 0;
        File qnaFile = new File("src/main/resources/com/demo/gui/files/qna.txt");
        File leaderboardFile = new File("src/main/resources/com/demo/gui/files/leaderboard.txt");
        this.gameContent = new GameContent(qnaFile.getPath());
        this.leaderboard = new Leaderboard(leaderboardFile.getPath());
        this.player = new Player();
        this.help = new Help(player);
    }

    public Leaderboard getLeaderboard() { return this.leaderboard; }

    public String getPlayerName() {
        return this.player.getName();
    }

    public void setPlayerName(String name) { this.player.setName(name); }

//    private void inGameLogic() {
//        TreeMap<Integer, Question> qna = this.gc.getQna();
//        int i = 0;
//        String userAnswer = "";
//        this.startingTime = System.nanoTime();
//        while (i < qna.size()) {
//            boolean isCorrect = false;
//            TreeMap<String, String> shuffledOptions = this.gc.getShuffledOptions();
//            this.gameState = "play";
//            gc.displayQuestion(gui, i, player);
//            userAnswer = getPlayerAnswer(BASE_ANSWER_OPTIONS);
//            switch (userAnswer) {
//                case "q":
//                    i = qna.size();
//                    gameState = "quit";
//                    break;
//                case "h":
//                    if (player.getAvailableHelps().length > 0) {
//                        String solutionKey = gc.getKeyFromValue(i);
//                        userAnswer = help.helpMenu(shuffledOptions, solutionKey);
//                    } else {
//                        gui.displayMsg("No available help found! You have already used up all of them.");
//                        userAnswer = getPlayerAnswer(Set.of("a", "b", "c", "d", "q"), true);
//                    }
//                    isCorrect = evaluatePlayerAnswer(shuffledOptions, userAnswer, i);
//                    break;
//                default:
//                    isCorrect = evaluatePlayerAnswer(shuffledOptions, userAnswer, i);
//                    break;
//            }
//
//            if  (isCorrect) {
//                i++;
//                this.player.incrementPrize(i);
//            } else {
//                i = qna.size();
//            }
//        }
//        this.endTime = System.nanoTime();
//        handleEndOfGame();
//    }

    public void incrementQuestionCounter() { this.questionCounter++; }

    public Question getCurrentQuestionObj() {
        TreeMap<Integer, Question> qna = this.gameContent.getQna();
        return qna.get(this.questionCounter);
    }

    private boolean evaluatePlayerAnswer(TreeMap<String, String> shuffledOptions, String userAnswer, int i) {
        if (userAnswer.equals("q")) {
            gameState = "quit";
            return false;
        } else {
            String chosenAnswer = shuffledOptions.get(userAnswer);
            String realAnswer = gameContent.getQna().get(i).getAnswer();
            if (chosenAnswer.equals(realAnswer)) {
                //gui.displayMsg("Correct!");
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
                //gui.displayMsg("Congrats! You win the millionaire game!");
                //gui.displayMsg("Your prize is: $" + player.getPrize());

                String elapsedTime = formatElapsedTime();
                this.player.setFormattedElapsedTime(elapsedTime);
                this.leaderboard.addPlayerToLeaderboard(this.player);

                this.leaderboard.write();
                break;
            case "quit":
                //gui.displayMsg("You have chosen to quit the game!");
                //gui.displayMsg("Your prize is: $" + player.getGuaranteedPrize());
                break;
            case "lost":
                //gui.displayMsg("You have answered incorrectly. The game is over!");
                //gui.displayMsg("Your prize is: $" + player.getGuaranteedPrize());
                break;
            default:
                break;
        }
    }

    private String formatElapsedTime() {
        int elapsedSec = (int)Math.floor(((this.endTime - this.startingTime) / Math.pow(10, 9)) % 60);
        int elapsedMin = (int)Math.floor((((this.endTime - this.startingTime) / Math.pow(10, 9)) / 60) % 60);
        return (elapsedMin > 10 ? String.valueOf(elapsedMin) : "0" + elapsedMin) + ":" + (elapsedSec > 10 ? String.valueOf(elapsedSec) : "0" + elapsedSec);
    }
}
