package com.demo.core.model;

import java.util.List;

public class Question {
    private String question;
    private String answer;
    private List<String> getRandomOrderedAnswers;

    public List<String> getRandomOrderedAnswers() { return getRandomOrderedAnswers; }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public Question(String question, String answer, List<String> getRandomOrderedAnswers) {
        this.question = question;
        this.answer = answer;
        this.getRandomOrderedAnswers = getRandomOrderedAnswers;
    }

    public Question() {
    }
}
