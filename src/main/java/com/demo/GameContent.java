package com.demo;

import java.io.File;
import java.util.Scanner;
import java.util.TreeMap;

public class GameContent {
    private TreeMap<Integer, Question> qna = new TreeMap<>();

    public TreeMap<Integer, Question> getQna() {
        return qna;
    }

    public GameContent(String filename) {
        readInGameContent(filename);
    }

    public void readInGameContent(String inputFile) {
        File file = new File(inputFile);
        try (Scanner sc = new Scanner(file)) {
            if (file.exists()) {
                int i = 0;
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] splitLine = line.split("-");
                    String question = splitLine[0];
                    String answer = splitLine[1];
                    qna.put(i, new Question(question, answer));
                    i++;
                }
            } else {
                System.out.println("File " + inputFile + " does not exist.");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
