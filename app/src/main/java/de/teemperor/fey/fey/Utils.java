package de.teemperor.fey.fey;

import java.util.List;

public class Utils {

    static String joinString(String s, List<String> items) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            result.append(items.get(i));
            if (i < items.size() - 1) {
                result.append(s);
            }
        }
        return result.toString();
    }
}
