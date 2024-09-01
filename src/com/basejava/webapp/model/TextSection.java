package com.basejava.webapp.model;

import java.io.Serial;
import java.util.Objects;

public class TextSection extends AbstractSection {
    @Serial
    private static final long serialVersionUID = 1L;
    public static final TextSection EMPTY = new TextSection("");
    private String description;

    public TextSection() {
    }

    public TextSection(String description) {
        Objects.requireNonNull(description, "description must not be null");
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection that = (TextSection) o;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }
}
