package org.kgromov.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.bson.types.ObjectId;
import org.kgromov.service.MongoDateConverter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Objects;

@Document(collection = "weather_archive")
public class DailyTemperature {
    @Id
    private ObjectId id;
    @JsonDeserialize(using = MongoDateConverter.class)
    private LocalDateTime date;
    private Double morningTemperature;
    private Double afternoonTemperature;
    private Double eveningTemperature;
    private Double nightTemperature;

    public DailyTemperature() {
    }

    public ObjectId getId() {
        return this.id;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public Double getMorningTemperature() {
        return this.morningTemperature;
    }

    public Double getAfternoonTemperature() {
        return this.afternoonTemperature;
    }

    public Double getEveningTemperature() {
        return this.eveningTemperature;
    }

    public Double getNightTemperature() {
        return this.nightTemperature;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setMorningTemperature(Double morningTemperature) {
        this.morningTemperature = morningTemperature;
    }

    public void setAfternoonTemperature(Double afternoonTemperature) {
        this.afternoonTemperature = afternoonTemperature;
    }

    public void setEveningTemperature(Double eveningTemperature) {
        this.eveningTemperature = eveningTemperature;
    }

    public void setNightTemperature(Double nightTemperature) {
        this.nightTemperature = nightTemperature;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof DailyTemperature other)) return false;
        if (!other.canEqual(this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        return Objects.equals(this$id, other$id);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DailyTemperature;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        return result;
    }

    public String toString() {
        return "DailyTemperature(id=" + this.getId() + ", date=" + this.getDate() + ", morningTemperature=" + this.getMorningTemperature() + ", afternoonTemperature=" + this.getAfternoonTemperature() + ", eveningTemperature=" + this.getEveningTemperature() + ", nightTemperature=" + this.getNightTemperature() + ")";
    }
}
