package org.kgromov.model;

import java.util.Objects;

public class DayTemperature {
    private String date;
    private Double temperature;

    public DayTemperature(String date, Double temperature) {
        this.date = date;
        this.temperature = temperature;
    }

    public DayTemperature() {
    }

    public String getDate() {
        return this.date;
    }

    public Double getTemperature() {
        return this.temperature;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof DayTemperature other)) return false;
        if (!other.canEqual(this)) return false;
        final Object this$date = this.getDate();
        final Object other$date = other.getDate();
        if (!Objects.equals(this$date, other$date)) return false;
        final Object this$temperature = this.getTemperature();
        final Object other$temperature = other.getTemperature();
        return Objects.equals(this$temperature, other$temperature);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DayTemperature;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $date = this.getDate();
        result = result * PRIME + ($date == null ? 43 : $date.hashCode());
        final Object $temperature = this.getTemperature();
        result = result * PRIME + ($temperature == null ? 43 : $temperature.hashCode());
        return result;
    }

    public String toString() {
        return "DayTemperature(date=" + this.getDate() + ", temperature=" + this.getTemperature() + ")";
    }
}
