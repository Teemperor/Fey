package de.teemperor.fey.fey;

import java.util.List;

public class Question {
    private String question;
    private List<String> answers;
    private int correctAnswer;
    private Symbol checkedSymbol;
    private Teacher teacher;


    public Question(String question, List<String> answers, int correctAnswer, Symbol symbol, Teacher teacher) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        checkedSymbol = symbol;
        this.teacher = teacher;
    }


    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public boolean answer(int foundAnswerIndex) {
        boolean correct = (foundAnswerIndex == correctAnswer);
        teacher.giveFeedback(checkedSymbol, correct);
        return correct;
    }
}
