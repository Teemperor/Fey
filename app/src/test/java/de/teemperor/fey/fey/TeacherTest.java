package de.teemperor.fey.fey;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class TeacherTest {

    private SymbolDict makeTestDict() {
        SymbolDict dict = new SymbolDict("test");

        for (int i = 0; i < 20; i++)
        {
            String number = i < 10 ? "0" + i : "" + i;
            List<String> symbols = Arrays.asList("symbol" + number);
            List<String> readings = Arrays.asList("reading" + number);
            List<String> meanings = Arrays.asList("meaning" + number);
            dict.addSymbol(new Symbol(i, symbols, meanings, readings));
        }

        return dict;
    }

    private int getCorrectIndex(Question q) {
        assertTrue(q.getQuestion().startsWith("symbol"));
        String number = q.getQuestion().substring("symbol".length());
        int answerIndex = 0;
        int foundAnswerIndex = -1;
        for (String answer : q.getAnswers()) {
            if (answer.endsWith(number)) {
                assertEquals(foundAnswerIndex, -1);
                foundAnswerIndex = answerIndex;
            }
            answerIndex++;
        }
        assertNotEquals(foundAnswerIndex, -1);
        return foundAnswerIndex;
    }

    private int getWrongIndex(Question q) {
        int answer = getCorrectIndex(q);
        if (answer == 0)
            return 1;
        else
            return 0;
    }

    private void handleTask(LearnTask task, boolean answerCorrect) {
        if (task.getSymbolToLearn() != null) {
            Symbol s = task.getSymbolToLearn();
            assertTrue(s.getSymbols().get(0).startsWith("symbol"));
            assertTrue(s.getReadings().get(0).startsWith("reading"));
            assertTrue(s.getMeanings().get(0).startsWith("meaning"));
        } else if (task.getQuestion() != null) {
            Question q = task.getQuestion();
            if (answerCorrect)
                assertTrue(q.answer(getCorrectIndex(q)));
            else
                assertFalse(q.answer(getWrongIndex(q)));
        } else {
            fail("Wasn't a question or a symbol to learn");
        }
    }

    @Test
    public void testInitialProficiency() throws Exception {
        SymbolDict dict = makeTestDict();
        Teacher teacher = new Teacher();
        teacher.setDict(dict);

        for (Teacher.LearningSymbol s : teacher.getSymbols()) {
            assertTrue(s.getProficiency() <= 0.01);
        }
    }

    @Test
    public void testLearningProgress() throws Exception {
        SymbolDict dict = makeTestDict();
        Teacher teacher = new Teacher();
        teacher.setDict(dict);

        for (int i = 0; i < 274; i++) {
            handleTask(teacher.nextTask(), true);

        }
        for (Teacher.LearningSymbol s : teacher.getSymbols()) {
            assertTrue(s.getSymbol().getSymbols().get(0) + ":" + s.getProficiency(), s.getProficiency() >= 0.99);
        }
    }

    @Test
    public void testLearningRegress() throws Exception {
        SymbolDict dict = makeTestDict();
        Teacher teacher = new Teacher();
        teacher.setDict(dict);

        for (int i = 0; i < 274; i++) {
            handleTask(teacher.nextTask(), true);
        }
        for (Teacher.LearningSymbol s : teacher.getSymbols()) {
            assertTrue(s.getProficiency() >= 1);
        }

        for (int i = 0; i < 5000; i++) {
            handleTask(teacher.nextTask(), false);
        }

        for (Teacher.LearningSymbol s : teacher.getSymbols()) {
            assertTrue(s.getSymbol().getSymbols().get(0) + ":" + s.getProficiency(), s.getProficiency() <= 0.1);
        }
    }
}