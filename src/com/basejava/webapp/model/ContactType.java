package com.basejava.webapp.model;

public enum ContactType {
    PHONE_NUMBER("Тел.: "),
    SKYPE("Skype: "),
    MAIL("Почта: "),
    LINKEDIN("Профиль Linkedin: "),
    GITHUB("Профиль GitHub: "),
    STACKOVERFLOW("Профиль Stackoverflow: "),
    HOME_PAGE("Домашняя страница: ");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
