package com.demo;

import com.demo.GUI.GuiDisplay;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeMap;

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

    public void displayQuestion(GuiDisplay gui, int i) {
        String question = qna.get(i).getContent();
        String answer = qna.get(i).getAnswer();
        String[] dummies = qna.get(i).getDummies();
        gui.displayMsg(i + 1 + ". question:");
        gui.displayMsg(question);
        gui.displayMsg("a - " + answer);
        gui.displayMsg("b - " + dummies[0]);
        gui.displayMsg("c - "  + dummies[1]);
        gui.displayMsg("d - "  + dummies[2]);
    }
}
