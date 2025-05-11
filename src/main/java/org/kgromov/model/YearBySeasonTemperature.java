package org.kgromov.model;

import java.util.List;

public class YearBySeasonTemperature {
    private Integer year;
    private List<SeasonTemperature> seasons;

    public YearBySeasonTemperature(Integer year, List<SeasonTemperature> seasons) {
        this.year = year;
        this.seasons = seasons;
    }

    public YearBySeasonTemperature() {
    }

    public Integer getYear() {
        return this.year;
    }

    public List<SeasonTemperature> getSeasons() {
        return this.seasons;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setSeasons(List<SeasonTemperature> seasons) {
        this.seasons = seasons;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof YearBySeasonTemperature)) return false;
        final YearBySeasonTemperature other = (YearBySeasonTemperature) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$year = this.getYear();
        final Object other$year = other.getYear();
        if (this$year == null ? other$year != null : !this$year.equals(other$year)) return false;
        final Object this$seasons = this.getSeasons();
        final Object other$seasons = other.getSeasons();
        if (this$seasons == null ? other$seasons != null : !this$seasons.equals(other$seasons)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof YearBySeasonTemperature;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $year = this.getYear();
        result = result * PRIME + ($year == null ? 43 : $year.hashCode());
        final Object $seasons = this.getSeasons();
        result = result * PRIME + ($seasons == null ? 43 : $seasons.hashCode());
        return result;
    }

    public String toString() {
        return "YearBySeasonTemperature(year=" + this.getYear() + ", seasons=" + this.getSeasons() + ")";
    }
}
