package com.urise.webapp.model;

import java.io.Serial;

public class TextSection extends AbstractSection {
    @Serial
    private static final long serialVersionUID = 1L;
    private String value;

    @Override
    public String toString() {
        return value;
    }

    public TextSection(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection that = (TextSection) o;

        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
