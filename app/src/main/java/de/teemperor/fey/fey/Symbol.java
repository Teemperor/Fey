package de.teemperor.fey.fey;

import java.util.HashMap;
import java.util.List;

public class Symbol {

    private final HashMap<Language, List<String>> meanings;
    private final double frequency;
    private String symbol;

    public Symbol(String symbol, HashMap<Language, List<String>> meanings, double frequency) {
        this.symbol = symbol;
        this.meanings = meanings;
        this.frequency = frequency;
    }
}
