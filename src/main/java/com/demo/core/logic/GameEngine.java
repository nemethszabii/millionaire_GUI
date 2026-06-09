package com.demo.core.logic;

import com.demo.core.model.Player;
import com.demo.core.model.Question;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GameEngine {
    private final GameContent gameContent;
    private Player player;
    private Help help;
    private Leaderboard leaderboard;
    private int questionCounter;
    private Map<Integer, String> prizes;

    public GameEngine() {
        this.questionCounter = 0;
        File qnaFile = new File("src/main/resources/com/demo/gui/files/qna.txt");
        File leaderboardFile = new File("src/main/resources/com/demo/gui/files/leaderboard.txt");
        this.gameContent = new GameContent(qnaFile.getPath());
        this.leaderboard = new Leaderboard(leaderboardFile.getPath());
        this.player = new Player();
        this.help = new Help();
        setUpPrizes();
    }

    public int getQuestionCounter() { return this.questionCounter; }

    public Player getPlayer() { return this.player; }

    public Leaderboard getLeaderboard() { return this.leaderboard; }

    public Question getCurrentQuestionObj() {
        List<Question> qna = this.gameContent.getQna();
        return qna.get(this.questionCounter);
    }

    public Help getHelp() { return this.help; }

    public void incrementQuestionCounter() { this.questionCounter++; }

    public void incrementPlayerPrize() {
        this.player.setPrize(this.prizes.get(questionCounter));
        this.player.setNextPrize(this.prizes.get(questionCounter + 1));
        if (questionCounter % 5 == 0) {
            this.player.setSecurePrize(this.prizes.get(questionCounter));
        }
    }

    private void setUpPrizes() {
        this.prizes = new HashMap<>();
        this.prizes.put(1, "$100");
        this.prizes.put(2, "$200");
        this.prizes.put(3, "$300");
        this.prizes.put(4, "$500");
        this.prizes.put(5, "$1,000");
        this.prizes.put(6, "$2,000");
        this.prizes.put(7, "$4,000");
        this.prizes.put(8, "$8,000");
        this.prizes.put(9, "$16,000");
        this.prizes.put(10, "$32,000");
        this.prizes.put(11, "$64,000");
        this.prizes.put(12, "$125,000");
        this.prizes.put(13, "$250,000");
        this.prizes.put(14, "$500,000");
        this.prizes.put(15, "$1,000,000");
    }
}
