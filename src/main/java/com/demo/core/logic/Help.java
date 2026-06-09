package com.demo.core.logic;

import com.demo.core.model.Question;

import java.util.*;

public class Help {
    private byte answerIndexAfterFiftyUse;

    public String[] fiftyFifty(Question currentQuestion) {
        String[] optionsAfter = new String[2];
        String rndOption = currentQuestion.getAnswer();
        Random r = new Random();
        while (currentQuestion.getAnswer().equals(rndOption)) {
            rndOption = currentQuestion.getRandomOrderedAnswers()
                    .get(r.nextInt(currentQuestion.getRandomOrderedAnswers().size()));
        }

        int rand = r.nextInt(2);
        if (rand == 0) {
            optionsAfter[0] = rndOption;
            optionsAfter[1] = currentQuestion.getAnswer();
            this.answerIndexAfterFiftyUse = 1;
        } else {
            optionsAfter[0] = currentQuestion.getAnswer();
            optionsAfter[1] = rndOption;
            this.answerIndexAfterFiftyUse = 0;
        }

        return optionsAfter;
    }

    public String phone(Question currentQuestion) {
        return "I guess, '" + currentQuestion.getAnswer() + "' is the correct answer!";
    }

    public String audience(Question currentQuestion) {
        int[] votes = genAudienceVotes(4);
        associateAnswerWithHighestVote(currentQuestion, votes);
        return "a: " + votes[0] + " % | "
                + "b: " + votes[1] + " % | "
                + "c: " + votes[2] + " % | "
                + "d: " + votes[3] + " %";
    }

    public String audience(Question currentQuestion, boolean fiftyHelpWasUsedBefore) {
        int[] votes = genAudienceVotes(2);
        associateAnswerWithHighestVote(currentQuestion, votes);
        return "a: " + votes[0] + " % | "
                + "b: " + votes[1] + " %";
    }

    private void associateAnswerWithHighestVote(Question currentQuestion, int[] votes) {
        int maxValIdx = getMaxValIndex(votes);
        int answerIndex = -1;
        if (votes.length == 4) {
            answerIndex = currentQuestion.getRandomOrderedAnswers().indexOf(currentQuestion.getAnswer());
        } else {
            answerIndex = answerIndexAfterFiftyUse;
        }

        int tmp = votes[maxValIdx];
        votes[maxValIdx] = votes[answerIndex];
        votes[answerIndex] = tmp;
    }

    private int getMaxValIndex(int[] votes) {
        int maxIdx = 0;
        for (int i = 1; i < votes.length; i++) {
            if (votes[i] > votes[maxIdx]) {
                maxIdx = i;
            }
        }
        return maxIdx;
    }

    private int[] genAudienceVotes(int size) {
        Random r = new Random();
        TreeSet<Integer> set = new TreeSet<>();
        while (set.size() < size - 1) {
            set.add(r.nextInt(100 - 1) + 1);
        }

        List<Integer> cutList = new ArrayList<>(set);
        cutList.addFirst(0);
        cutList.add(100);

        int[] result = new int[size];
        for (int i = 0; i < cutList.size() - 1; i++) {
            result[i] = cutList.get(i + 1) - cutList.get(i);
        }
        return result;
    }
}
