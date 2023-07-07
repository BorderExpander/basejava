package com.urise.webapp.model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class ListSection extends AbstractSection {
    @Serial
    private static final long serialVersionUID = 1L;
    private List<String> values = new ArrayList<>();

    public ListSection() {
    }

    public ListSection(List<String> values) {
        this.values = values;
    }

    public List<String> getValues() {
        return values;
    }

    public void addValue(String value) {
        values.add(value);
    }

    public String getValue(int index) {
        return values.get(index);
    }

    public String toString() {
        return values.toString();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return values.equals(that.values);
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }
}
