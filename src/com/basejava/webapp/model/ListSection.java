package com.basejava.webapp.model;

import java.io.Serial;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<String> list;

    public ListSection() {
    }

    public ListSection(List<String> list) {
        Objects.requireNonNull(list, "list must not be null");
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    @Override
    public String toString() {
//        return points.toString();
        StringBuilder sb = new StringBuilder();
        for (String point : list) {
            sb.append("  * ").append(point).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;
        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }
}
