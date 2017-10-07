package de.teemperor.fey.fey;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class TeacherTest {

    public SymbolDict makeTestDict() {
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

    public int getCorrectIndex(Question q) {
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
    public void testDict() throws Exception {
        SymbolDict dict = makeTestDict();
        Teacher teacher = new Teacher();
        teacher.setDict(dict);

        for (int i = 0; i < 20000; i++) {
            LearnTask task = teacher.nextTask();
            if (task.getSymbolToLearn() != null) {
                Symbol s = task.getSymbolToLearn();
                assertTrue(s.getSymbols().get(0).startsWith("symbol"));
                assertTrue(s.getReadings().get(0).startsWith("reading"));
                assertTrue(s.getMeanings().get(0).startsWith("meaning"));
            } else if (task.getQuestion() != null) {
                Question q = task.getQuestion();
                int answer = getCorrectIndex(q);
                assertTrue(q.answer(answer));
            } else {
                fail("Wasn't a question or a symbol to learn");
            }
        }
        for (Teacher.LearningSymbol s : teacher.getSymbols()) {
            assertTrue(s.getProficiency() >= 1);
        }
    }
}