package de.teemperor.fey.fey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SymbolDict {

    private HashMap<String, Symbol> symbols;
    private ArrayList<Symbol> allSymbols;

    private String name;

    public SymbolDict(String name) {
        this.name = name;
        allSymbols = new ArrayList<Symbol>();
    }

    public void addSymbol(Symbol s) {
        allSymbols.add(s);
    }

    public Symbol getRandom() {
        Random r = new Random();
        int i = r.nextInt(allSymbols.size());
        return allSymbols.get(i);
    }

    public ArrayList<Symbol> getAllSymbols() {
        return allSymbols;
    }

    static SymbolDict singleton;

    public void parse(InputStream stream) {
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String line;

        List<String> symbols = null;
        List<String> translations = null;
        List<String> readings = null;
        int uid = 0;
        try {
            while((line = in.readLine()) != null) {
                if (line.equals("E")) {
                    if (symbols != null) {
                        addSymbol(new Symbol(uid, symbols, translations, readings));
                        uid++;
                    }
                    symbols = new ArrayList<String>();
                    translations = new ArrayList<String>();
                    readings = new ArrayList<String>();
                } else if (line.startsWith("S:")) {
                    symbols.add(line.substring("S:".length()));
                } else if (line.startsWith("T:")) {
                    translations.add(line.substring("T:".length()));
                } else if (line.startsWith("R:")) {
                    readings.add(line.substring("R:".length()));
                } else {
                    throw new RuntimeException("Invalid line " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseCSV(InputStream stream) {
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String line;
        int uid = 0;
        try {
            while((line = in.readLine()) != null) {
                String[] parts = line.split("\t");
                ArrayList<String> symbols = new ArrayList<>();
                ArrayList<String> readings = new ArrayList<>();
                ArrayList<String> meanings = new ArrayList<>();

                symbols.add(parts[0].trim());
                for (String reading : parts[1].split(";")) {
                    readings.add(reading.trim());
                }
                for (String reading : parts[2].split(";")) {
                    meanings.add(reading.trim());
                }

                addSymbol(new Symbol(uid, symbols, meanings, readings));
                uid++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }
}
