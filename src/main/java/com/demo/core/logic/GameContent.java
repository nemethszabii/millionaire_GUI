package com.demo.core.logic;

import com.demo.console.GuiDisplay;
import com.demo.core.model.Player;
import com.demo.core.model.Question;

import java.io.File;
import java.util.*;

public class GameContent {
    private TreeMap<Integer, Question> qna = new TreeMap<>();

    public TreeMap<Integer, Question> getQna() {
        return qna;
    }

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
                    String question = i + 1 + ". " + splitLine[0];
                    String answer = splitLine[1];
                    List<String> allAnswersList = buildAnswerOptionsList(Arrays.copyOfRange(splitLine, 1, splitLine.length));
                    List<String> randomOrderedAnswers = buildShuffledOptions(allAnswersList);
                    qna.put(i, new Question(question, answer, randomOrderedAnswers));
                    i++;
                }
            } else {
                System.out.println("File " + inputFile + " does not exist.");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }

    private List<String> buildAnswerOptionsList(String[] arr) {
        List<String> options = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            options.add(arr[i]);
        }
        return options;
    }

    private List<String> buildShuffledOptions(List<String> availableOptions) {
        int size = availableOptions.size();
        List<String> shuffledOptions = new ArrayList<>();
        int j = 0;
        while (j < size) {
            String option = getRandomOption(availableOptions);
            shuffledOptions.add(option);
            j++;
        }
        return shuffledOptions;
    }

    public static String getRandomOption(List<String> availableOptions) {
        Random rand = new Random();
        int index = rand.nextInt(availableOptions.size());
        String option = availableOptions.get(index);
        availableOptions.remove(index);
        return option;
    }
}
