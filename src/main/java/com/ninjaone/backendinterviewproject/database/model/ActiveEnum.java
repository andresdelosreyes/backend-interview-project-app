package com.ninjaone.backendinterviewproject.database.model;

public enum ActiveEnum {
    YES("YES", true),
    NO("NO", false);

    private final String name;
    private final Boolean value;

    ActiveEnum(String name, Boolean value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Boolean getValue() {
        return value;
    }
}
