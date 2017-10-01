package de.teemperor.fey.fey;

public class LearnTask {

    private Question question = null;
    private Symbol symbolToLearn = null;

    public LearnTask(Question question) {
        this.question = question;
    }

    public LearnTask(Symbol symbolToLearn) {
        this.symbolToLearn = symbolToLearn;
    }

    public Question getQuestion() {
        return question;
    }

    public Symbol getSymbolToLearn() {
        return symbolToLearn;
    }
}
