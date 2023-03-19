package org.snturk.petition.enums;

public enum PetitionType {
    COMPLAINT("Complaint"),
    REQUEST("Request"),
    SUGGESTION("Suggestion"),
    APPLICATION("Application");

    private String value;

    PetitionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
