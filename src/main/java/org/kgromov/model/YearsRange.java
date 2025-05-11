package org.kgromov.model;

public class YearsRange {
    private int minYear;
    private int maxYear;

    public YearsRange(int minYear, int maxYear) {
        this.minYear = minYear;
        this.maxYear = maxYear;
    }

    public YearsRange() {
    }

    public int getMinYear() {
        return this.minYear;
    }

    public int getMaxYear() {
        return this.maxYear;
    }

    public void setMinYear(int minYear) {
        this.minYear = minYear;
    }

    public void setMaxYear(int maxYear) {
        this.maxYear = maxYear;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof YearsRange)) return false;
        final YearsRange other = (YearsRange) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getMinYear() != other.getMinYear()) return false;
        if (this.getMaxYear() != other.getMaxYear()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof YearsRange;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getMinYear();
        result = result * PRIME + this.getMaxYear();
        return result;
    }

    public String toString() {
        return "YearsRange(minYear=" + this.getMinYear() + ", maxYear=" + this.getMaxYear() + ")";
    }
}
