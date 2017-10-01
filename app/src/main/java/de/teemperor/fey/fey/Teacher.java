package de.teemperor.fey.fey;

public class Teacher {

    public Teacher() {

    }

    public LearnTask nextTask() {
        return new LearnTask(SymbolDict.singleton.getRandom());
    }
}
