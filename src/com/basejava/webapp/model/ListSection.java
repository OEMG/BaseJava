package com.basejava.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private final List<String> points;

    public ListSection(List<String> points) {
        this.points = points;
    }

    public List<String> getPoints() {
        return points;
    }

    @Override
    public String toString() {
//        return points.toString();
        StringBuilder sb = new StringBuilder();
        for (String point : points) {
            sb.append("  * ").append(point).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;
        return Objects.equals(points, that.points);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(points);
    }
}
