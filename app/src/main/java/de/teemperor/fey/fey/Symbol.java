package de.teemperor.fey.fey;

import java.util.List;

public class Symbol {

    private List<String> symbol;
    private List<String> meanings;
    private List<String> readings;
    private int uid;

    public Symbol(int uid, List<String> symbol, List<String> meanings, List<String> readings) {
        this.uid = uid;
        this.symbol = symbol;
        this.meanings = meanings;
        this.readings = readings;
    }

    public List<String> getMeanings() {
        return meanings;
    }

    public List<String> getSymbols() {
        return symbol;
    }

    public List<String> getReadings() {
        return readings;
    }

    public int getUid() {
        return uid;
    }
}
