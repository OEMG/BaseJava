package com.basejava.webapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Period {
    private final String title;
    private final String description;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Period(String title, String description, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Period period = (Period) o;
        return Objects.equals(title, period.title) && Objects.equals(description, period.description) &&
                Objects.equals(startDate, period.startDate) && Objects.equals(endDate, period.endDate);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(title);
        result = 31 * result + Objects.hashCode(description);
        result = 31 * result + Objects.hashCode(startDate);
        result = 31 * result + Objects.hashCode(endDate);
        return result;
    }

    @Override
    public String toString() {
        String pattern = "MM/yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        if (description.equals("")) {
            return startDate.format(formatter) + " - " +
                    endDate.format(formatter) +
                    String.format("%" + 3 + "s", "") +
                    title;
        }
        return startDate.format(formatter) + " - " +
                endDate.format(formatter) +
                String.format("%" + 3 + "s", "") +
                title + "\n\n" +
                String.format("%" + 20 + "s", "") +
                description;
    }
}
