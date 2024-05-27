package com.basejava.webapp.model;

import java.util.List;
import java.util.Objects;

public class Company {
    private final String name;
    private final String website;
    private final List<Period> periods;

    public Company(String name, String website, List<Period> periods) {
        this.name = name;
        this.website = website;
        this.periods = periods;;
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

        final StringBuffer sb = new StringBuffer(String.format("%" + 20 + "s", ""));
        sb.append(name).append(": ").append(website).append("\n");
        for (Period period : periods) {
            sb.append(period).append("\n");
        }
        return sb.toString();
    }
}
