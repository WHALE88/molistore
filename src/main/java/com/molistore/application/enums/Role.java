package com.molistore.application.enums;

public enum Role {
    ROLE_CUSTOMER("ROLE_CUSTOMER", 0),
    ROLE_SEO("ROLE_SEO", 1),
    ROLE_ADMINISTRATOR("ROLE_ADMINISTRATOR", 2),
    ROLE_SUPERVISOR("ROLE_SUPERVISOR", 3);

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
