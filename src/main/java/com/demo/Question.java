package com.demo;

public class Question {
    private String content;
    private String answer;

    public String getContent() {
        return content;
    }

    public String getAnswer() {
        return answer;
    }

    public Question(String content, String answer) {
        this.content = content;
        this.answer = answer;
    }

    public Question() {
    }
}
