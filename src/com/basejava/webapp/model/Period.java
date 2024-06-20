package com.basejava.webapp.model;

import com.basejava.webapp.util.JsonLocalDateAdapter;
import com.basejava.webapp.util.JsonSectionAdapter;
import com.basejava.webapp.util.XmlLocalDateAdapter;
import com.google.gson.annotations.JsonAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public final class Period implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String title;
    private String description;
    @XmlJavaTypeAdapter(XmlLocalDateAdapter.class)
    @JsonAdapter(JsonLocalDateAdapter.class)
    private LocalDate startDate;
    @XmlJavaTypeAdapter(XmlLocalDateAdapter.class)
    @JsonAdapter(JsonSectionAdapter.class)
    private LocalDate endDate;

    public Period() {
    }

    public Period(String title, String description, LocalDate startDate, LocalDate endDate) {
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(title, "title must not be null");
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
        return title.equals(period.title) &&
                description.equals(period.description) &&
                startDate.equals(period.startDate) &&
                endDate.equals(period.endDate);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + Objects.hashCode(description);
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        String pattern = "MM/yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        if (description.isEmpty()) {
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
