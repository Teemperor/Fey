package de.teemperor.fey.fey;

import java.util.List;

public class Question {
    private String question;
    private List<String> answers;
    public Question(String question, List<String> answers) {
        this.question = question;
        this.answers = answers;
    }
}
