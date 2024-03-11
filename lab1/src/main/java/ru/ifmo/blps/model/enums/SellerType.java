package ru.ifmo.blps.model.enums;

import lombok.Getter;

@Getter
public enum SellerType {
    OWNER("owner", 1),

    AGENT("agent", 0);

    private String name;

    private int freeListings;

    SellerType(String name, int i) {
        this.name = name;
        this.freeListings = i;
    }

    public static SellerType fromString(String name) {

        for (SellerType type : SellerType.values()) {
            if (type.name.equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant found for name: " + name);
    }
}
