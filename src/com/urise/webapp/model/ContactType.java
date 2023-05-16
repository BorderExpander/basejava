package com.urise.webapp.model;

public enum ContactType {
    PHONE("Тел."),
    SKYPE("Shype"),
    EMAIL("Почта"),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль Stackoverflow"),
    HOME_PAGE("Домашняя страница");
    private final String name;

    ContactType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
