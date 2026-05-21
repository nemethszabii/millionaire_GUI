package com.demo.models;

public class Question {
    private String content;
    private String answer;
    private String[] dummies;

    public String[] getDummies() {
        return dummies;
    }

    public String getContent() {
        return content;
    }

    public String getAnswer() {
        return answer;
    }

    public Question(String content, String answer, String[] dummies) {
        this.content = content;
        this.answer = answer;
        this.dummies = dummies;
    }

    public Question() {
    }
}
