package org.kgromov.model;

public class SeasonTemperature {
    private Integer year;
    private Season season;
    private Double minTemp;
    private Double maxTemp;
    private Double avgTemp;

    public SeasonTemperature(Integer year, Season season, Double minTemp, Double maxTemp, Double avgTemp) {
        this.year = year;
        this.season = season;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.avgTemp = avgTemp;
    }

    public SeasonTemperature() {
    }

    public Integer getYear() {
        return this.year;
    }

    public Season getSeason() {
        return this.season;
    }

    public Double getMinTemp() {
        return this.minTemp;
    }

    public Double getMaxTemp() {
        return this.maxTemp;
    }

    public Double getAvgTemp() {
        return this.avgTemp;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public void setMinTemp(Double minTemp) {
        this.minTemp = minTemp;
    }

    public void setMaxTemp(Double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public void setAvgTemp(Double avgTemp) {
        this.avgTemp = avgTemp;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SeasonTemperature)) return false;
        final SeasonTemperature other = (SeasonTemperature) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$year = this.getYear();
        final Object other$year = other.getYear();
        if (this$year == null ? other$year != null : !this$year.equals(other$year)) return false;
        final Object this$season = this.getSeason();
        final Object other$season = other.getSeason();
        if (this$season == null ? other$season != null : !this$season.equals(other$season)) return false;
        final Object this$minTemp = this.getMinTemp();
        final Object other$minTemp = other.getMinTemp();
        if (this$minTemp == null ? other$minTemp != null : !this$minTemp.equals(other$minTemp)) return false;
        final Object this$maxTemp = this.getMaxTemp();
        final Object other$maxTemp = other.getMaxTemp();
        if (this$maxTemp == null ? other$maxTemp != null : !this$maxTemp.equals(other$maxTemp)) return false;
        final Object this$avgTemp = this.getAvgTemp();
        final Object other$avgTemp = other.getAvgTemp();
        if (this$avgTemp == null ? other$avgTemp != null : !this$avgTemp.equals(other$avgTemp)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SeasonTemperature;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $year = this.getYear();
        result = result * PRIME + ($year == null ? 43 : $year.hashCode());
        final Object $season = this.getSeason();
        result = result * PRIME + ($season == null ? 43 : $season.hashCode());
        final Object $minTemp = this.getMinTemp();
        result = result * PRIME + ($minTemp == null ? 43 : $minTemp.hashCode());
        final Object $maxTemp = this.getMaxTemp();
        result = result * PRIME + ($maxTemp == null ? 43 : $maxTemp.hashCode());
        final Object $avgTemp = this.getAvgTemp();
        result = result * PRIME + ($avgTemp == null ? 43 : $avgTemp.hashCode());
        return result;
    }

    public String toString() {
        return "SeasonTemperature(year=" + this.getYear() + ", season=" + this.getSeason() + ", minTemp=" + this.getMinTemp() + ", maxTemp=" + this.getMaxTemp() + ", avgTemp=" + this.getAvgTemp() + ")";
    }
}
