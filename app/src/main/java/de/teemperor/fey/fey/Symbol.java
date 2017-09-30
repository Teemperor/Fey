package de.teemperor.fey.fey;

import java.util.HashMap;
import java.util.List;

public class Symbol {


    private List<String> meanings;
    private double frequency;
    private List<String> symbol;

    public Symbol(List<String> symbol, List<String> meanings, double frequency) {
        this.symbol = symbol;
        this.meanings = meanings;
        this.frequency = frequency;
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
}
