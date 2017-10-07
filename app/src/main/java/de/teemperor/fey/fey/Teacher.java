package de.teemperor.fey.fey;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Teacher {

    private ArrayList<LearningSymbol> symbols;
    private HashMap<Symbol, LearningSymbol> symbolToLearning = new HashMap<>();
    private LearningSymbol lastLearned = null;


    public class LearningSymbol {
        private Symbol symbol;
        private float proficiency = 0;
        private boolean introduced = false;

        public LearningSymbol(Symbol symbol) {
            this.symbol = symbol;
        }

        public Symbol getSymbol() {
            return symbol;
        }

        public float getProficiency() {
            return proficiency;
        }

        public boolean isIntroduced() {
            return introduced;
        }

        public void setIntroduced(boolean introduced) {
            this.introduced = introduced;
        }

        public void giveFeedback(boolean correct) {
            if (correct) {
                proficiency += 0.1f;
                if (proficiency > 1)
                    proficiency = 1;
            } else {
                proficiency /= 2;
            }
        }
    }

    public Teacher() {
        symbols = new ArrayList<>();
    }

    public void setDict(SymbolDict dict) {
        for (Symbol s : dict.getAllSymbols()) {
            LearningSymbol ls;
            symbols.add(ls = new LearningSymbol(s));
            symbolToLearning.put(s, ls);
        }
        lastLearned = symbols.get(symbols.size() - 1);
    }

    private Random random = new Random(2271694);

    public ArrayList<LearningSymbol> getSymbols() {
        return symbols;
    }

    private Symbol getWrongSymbol(List<Symbol> symbolsToIgnore) {
        int maxIndex = 0;
        for (LearningSymbol s : symbols) {
            if (!s.isIntroduced())
                break;
            maxIndex++;
        }
        Random r = new Random();
        while(true) {
            Symbol s = symbols.get(r.nextInt(maxIndex)).getSymbol();
            if (symbolsToIgnore.contains(s))
                continue;;
            return s;
        }
    }

    private Question makeQuestion(LearningSymbol s) {
        ArrayList<String> answers = new ArrayList<>();
        ArrayList<Symbol> wrongSymbols = new ArrayList<>();
        wrongSymbols.add(s.getSymbol());
        for (int i = 0; i < 5; i++) {
            Symbol wrongS = getWrongSymbol(wrongSymbols);
            wrongSymbols.add(wrongS);
            answers.add(wrongS.getMeanings().get(random.nextInt(wrongS.getMeanings().size())));
        }
        int correctIndex = random.nextInt(answers.size());
        answers.add(correctIndex, s.getSymbol().getMeanings().get(0));

        return new Question(s.getSymbol().getSymbols().get(0), answers, correctIndex, s.getSymbol(), this);
    }

    public void giveFeedback(Symbol checkedSymbol, boolean correct) {
        symbolToLearning.get(checkedSymbol).giveFeedback(correct);
    }

    public LearnTask nextTask() {
        for (int i = 0; i < symbols.size(); i++) {
            LearningSymbol s = symbols.get(i);
            if (!s.isIntroduced()) {
                s.setIntroduced(true);
                return new LearnTask(s.getSymbol());
            }
            if (i >= 10)
                break;
        }

        ArrayList<LearningSymbol> possibleSymbols = new ArrayList<>();
        ArrayList<Integer> weights = new ArrayList<>();
        int weightSum = 0;

        for (LearningSymbol s : symbols) {
            possibleSymbols.add(s);
            int weight = (int) (s.getProficiency() * 15) + 1;
            weightSum += weight;
            weights.add(weight);
            if(!s.isIntroduced())
                break;
        }
        int index = new Random().nextInt(weightSum);
        for(int i = 0; i < weights.size(); i++) {
            index -= weights.get(i);
            if (index < 0) {
                LearningSymbol s = possibleSymbols.get(i);
                if (s.isIntroduced()) {
                    Question q = makeQuestion(possibleSymbols.get(i));
                    return new LearnTask(q);
                } else {
                    s.setIntroduced(true);
                    return new LearnTask(s.getSymbol());
                }
            }
        }
        throw new RuntimeException("Run out of weights?");


//        if (s.getProficiency() >= 1)
//            continue;
//        if (!s.isIntroduced()) {
//            s.setIntroduced(true);
//            return new LearnTask(s.getSymbol());
//        }
//        if (s != lastLearned) {
//            possibleSymbols.add(s);
//        }
        //return new LearnTask(makeQuestion(symbols.get(index)));
    }
}
