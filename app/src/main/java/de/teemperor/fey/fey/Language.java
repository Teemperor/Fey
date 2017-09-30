package de.teemperor.fey.fey;

public class Language {
    private String displayName;
    private String key;
    public Language(String displayName, String key) {
        this.key = key;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getKey() {
        return key;
    }
}
