package de.teemperor.fey.fey;

import java.util.List;

public class Symbol {

    private List<String> symbol;
    private List<String> meanings;
    private List<String> readings;
    private double frequency;

    public Symbol(List<String> symbol, List<String> meanings, List<String> readings, double frequency) {
        this.symbol = symbol;
        this.meanings = meanings;
        this.frequency = frequency;
        this.readings = readings;
    }

    public List<String> getMeanings() {
        return meanings;
    }

    public double getFrequency() {
        return frequency;
    }

    public List<String> getSymbol() {
        return symbol;
    }

    public List<String> getReadings() {
        return readings;
    }
}
