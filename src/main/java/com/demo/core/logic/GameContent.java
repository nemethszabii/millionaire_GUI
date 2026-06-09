package com.demo.core.logic;

import com.demo.console.GuiDisplay;
import com.demo.core.model.Player;
import com.demo.core.model.Question;

import java.io.File;
import java.util.*;

public class GameContent {
    //private final TreeMap<Integer, Question> qna = new TreeMap<>();
    private final List<Question> qna = new ArrayList<>();

    public List<Question> getQna() {
        return qna;
    }

    public GameContent(String qnaFile) {
        readInGameContent(qnaFile);
    }

    private void readInGameContent(String inputFile) {
        File file = new File(inputFile);
        try (Scanner sc = new Scanner(file)) {
            if (file.exists()) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] splitLine = line.split("#");
                    String question = splitLine[0];
                    String answer = splitLine[1];
                    List<String> allAnswersList = new ArrayList<>(Arrays
                            .asList(Arrays.copyOfRange(splitLine, 1, splitLine.length)));
                    List<String> randomOrderedAnswers = buildShuffledOptions(allAnswersList);
                    qna.add(new Question(question, answer, randomOrderedAnswers));
                }
                Collections.shuffle(qna);
            } else {
                System.out.println("File " + inputFile + " does not exist.");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
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
