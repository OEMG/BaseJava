package com.basejava.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Company implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public static final Company EMPTY = new Company("", "", Period.EMPTY);

    private String name;
    private String website;
    private List<Period> periods;

    public Company() {
    }

    public Company(String name, String website, Period... periods) {
        this(name, website, Arrays.asList(periods));
    }

    public Company(String name, String website, List<Period> periods) {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(periods, "periods must not be null");
        this.name = name;
        this.website = website;
        this.periods = periods;
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;
        return Objects.equals(name, company.name) && Objects.equals(website, company.website)
                && Objects.equals(periods, company.periods);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(website);
        result = 31 * result + Objects.hashCode(periods);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s", "")).append(name).append(": ").append(website).append("\n");
        periods.forEach(period -> sb.append(period).append("\n"));
        return sb.toString();
    }
}
