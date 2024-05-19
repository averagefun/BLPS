package ru.ifmo.main.model.enums;

public enum ConformationType {
    CONFIRM("confirm"),
    BACK("back"),
    DELETE("delete");

    private final String name;

    ConformationType(String name) {
        this.name = name;
    }

    public static ConformationType fromString(String name) {

        for (ConformationType type : ConformationType.values()) {
            if (type.name.equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant found for name: " + name);
    }
}
