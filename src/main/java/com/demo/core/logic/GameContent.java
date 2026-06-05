package com.demo.core.logic;

import com.demo.console.GuiDisplay;
import com.demo.core.model.Player;
import com.demo.core.model.Question;

import java.io.File;
import java.util.*;

public class GameContent {
    private TreeMap<Integer, Question> qna = new TreeMap<>();
    private TreeMap<String, String> shuffledOptions = new TreeMap<>();

    public TreeMap<Integer, Question> getQna() {
        return qna;
    }

    public TreeMap<String, String> getShuffledOptions() { return shuffledOptions; }

    public GameContent(String qnaFile) {
        readInGameContent(qnaFile);
    }

    private void readInGameContent(String inputFile) {
        File file = new File(inputFile);
        try (Scanner sc = new Scanner(file)) {
            if (file.exists()) {
                int i = 0;
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] splitLine = line.split("#");
                    String question = splitLine[0];
                    String answer = splitLine[1];
                    String[] dummies = Arrays.copyOfRange(splitLine, 2, splitLine.length);
                    qna.put(i, new Question(question, answer, dummies));
                    i++;
                }
            } else {
                System.out.println("File " + inputFile + " does not exist.");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void displayQuestion(GuiDisplay gui, int i, Player player) {
        String question = qna.get(i).getContent();
        String answer = qna.get(i).getAnswer();
        String[] dummies = qna.get(i).getDummies();
        gui.displayMsg(i + 1 + ". question");
        gui.displayMsg("Prize: $" + player.getPrize());
        gui.displayMsg("Guaranteed prize: $" + player.getGuaranteedPrize());
        gui.displayMsg(question);
        buildShuffledOptions(i);
    }


    private void buildShuffledOptions(int i) {
        List<String> availableOptions = new ArrayList<>();
        availableOptions.add(qna.get(i).getAnswer());
        availableOptions.add(qna.get(i).getDummies()[0]);
        availableOptions.add(qna.get(i).getDummies()[1]);
        availableOptions.add(qna.get(i).getDummies()[2]);
        List<String> availableChars = new ArrayList<>();
        availableChars.add("a");
        availableChars.add("b");
        availableChars.add("c");
        availableChars.add("d");
        int j = 0;
        while (j < 4) {
            String option = getRandomOption(availableOptions);
            String charKey = getRandomOption(availableChars);
            shuffledOptions.put(charKey, option);
            j++;
        }
    }

    public static String getRandomOption(List<String> availableOptions) {
        Random rand = new Random();
        int index = rand.nextInt(availableOptions.size());
        String option = availableOptions.get(index);
        availableOptions.remove(option);
        return option;
    }

    public String getKeyFromValue(int i) {
        String answer = qna.get(i).getAnswer();
        for (Map.Entry<String, String> entry : shuffledOptions.entrySet()) {
            if (entry.getValue().equals(answer)) {
                return entry.getKey();
            }
        };
        return "";
    }
}
