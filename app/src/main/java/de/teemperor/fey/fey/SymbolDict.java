package de.teemperor.fey.fey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SymbolDict {

    HashMap<String, Symbol> symbols;
    ArrayList<Symbol> allSymbols = new ArrayList<Symbol>();

    public SymbolDict() {
    }

    private void addSymbol(Symbol s) {
        allSymbols.add(s);
    }

    public Symbol getRandom() {
        Random r = new Random();
        int i = r.nextInt(allSymbols.size());
        return allSymbols.get(i);
    }

    static SymbolDict singleton;

    public void parse(InputStream stream) {
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String line = null;

        boolean first = true;
        List<String> symbols = null;
        List<String> translations = null;
        double freq = 0;
        try {
            while((line = in.readLine()) != null) {
                if (line.equals("E")) {
                    if (symbols != null)
                        addSymbol(new Symbol(symbols, translations, freq));
                    symbols = new ArrayList<String>();
                    translations = new ArrayList<String>();
                } else if (line.startsWith("S:")) {
                    symbols.add(line.substring("S:".length()));
                } else if (line.startsWith("T:")) {
                    translations.add(line.substring("T:".length()));
                } else if (line.startsWith("F:")) {
                    freq = Double.parseDouble(line.substring("F:".length()));
                } else {
                    throw new RuntimeException("Invalid line " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
