package com.molistore.application.enums;

public enum Role {
    CUSTOMER("CUSTOMER", 0),
    SEO("SEO", 1),
    ADMINISTRATOR("ADMINISTRATOR", 2),
    SUPERVISOR("SUPERVISOR", 3);

    private final String name;
    private final int priority;

    Role(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }
}
