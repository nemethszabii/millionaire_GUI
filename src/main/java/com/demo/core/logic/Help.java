package com.demo.core.logic;

import com.demo.core.model.Question;

import java.util.*;

public class Help {

    public String[] fiftyFifty(Question currentQuestion) {
        String[] optionsAfter = new String[2];
        optionsAfter[0] = currentQuestion.getAnswer();
        String rndOption = currentQuestion.getAnswer();
        Random r = new Random();
        while (optionsAfter[0].equals(rndOption)) {
            rndOption = currentQuestion.getRandomOrderedAnswers()
                    .get(r.nextInt(currentQuestion.getRandomOrderedAnswers().size()));
        }
        optionsAfter[1] = rndOption;
        return optionsAfter;
    }

    public String phone(Question currentQuestion) {
        return "I guess, '" + currentQuestion.getAnswer() + "' is the correct answer!";
    }

    public String audience(Question currentQuestion) {
        List<Integer> votes = genAudienceVotes();
//        return currentQuestion.getRandomOrderedAnswers().get(0) + " - " + votes.get(0) + "% "
//                + currentQuestion.getRandomOrderedAnswers().get(1) + " - " + votes.get(1) + "% "
//                + currentQuestion.getRandomOrderedAnswers().get(2) + " - " + votes.get(2) + "% "
//                + currentQuestion.getRandomOrderedAnswers().get(3) + " - " + votes.get(3) + "%";

        return "a - " + votes.get(0) + "% | "
                + "b - " + votes.get(1) + "% | "
                + "c - " + votes.get(2) + "% | "
                + "d - " + votes.get(3) + "%";
    }

    private List<Integer> genAudienceVotes() {
        Random r = new Random();
        TreeSet<Integer> set = new TreeSet<>();
        while (set.size() < 4 - 1) {
            set.add(r.nextInt(100 - 1) + 1);
        }

        List<Integer> cutList = new ArrayList<>(set);
        cutList.addFirst(0);
        cutList.add(100);

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < cutList.size() - 1; i++) {
            result.add(cutList.get(i + 1) - cutList.get(i));
        }
        return result;
    }
}
