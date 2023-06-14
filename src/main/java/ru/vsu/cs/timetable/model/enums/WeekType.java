package ru.vsu.cs.timetable.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum WeekType {

    NUMERATOR("Числитель"),
    DENOMINATOR("Знаменатель");

    private final String name;

    WeekType(String name) {
        this.name = name;
    }

    @Override
    @JsonValue
    public String toString() {
        return this.name;
    }
}
