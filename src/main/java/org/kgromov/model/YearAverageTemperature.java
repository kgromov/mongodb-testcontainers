package org.kgromov.model;

import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.Objects;

public class YearAverageTemperature {
    @BsonProperty("year")
    private Integer year;
    private Double temperature;

    public YearAverageTemperature(Integer year, Double temperature) {
        this.year = year;
        this.temperature = temperature;
    }

    public YearAverageTemperature() {
    }

    public Integer getYear() {
        return this.year;
    }

    public Double getTemperature() {
        return this.temperature;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof YearAverageTemperature other)) return false;
        if (!other.canEqual(this)) return false;
        final Object this$year = this.getYear();
        final Object other$year = other.getYear();
        if (!Objects.equals(this$year, other$year)) return false;
        final Object this$temperature = this.getTemperature();
        final Object other$temperature = other.getTemperature();
        return Objects.equals(this$temperature, other$temperature);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof YearAverageTemperature;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $year = this.getYear();
        result = result * PRIME + ($year == null ? 43 : $year.hashCode());
        final Object $temperature = this.getTemperature();
        result = result * PRIME + ($temperature == null ? 43 : $temperature.hashCode());
        return result;
    }

    public String toString() {
        return "YearAverageTemperature(year=" + this.getYear() + ", temperature=" + this.getTemperature() + ")";
    }
}
