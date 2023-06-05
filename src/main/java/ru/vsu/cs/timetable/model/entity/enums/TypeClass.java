package ru.vsu.cs.timetable.model.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TypeClass {
    LECTURE("Лекция"),
    SEMINAR("Семинар");

    private final String name;

    TypeClass(String name) {
        this.name = name;
    }

    public static TypeClass fromName(String name) {
        for (TypeClass typeClass : TypeClass.values()) {
            if (typeClass.name.equalsIgnoreCase(name)) {
                return typeClass;
            }
        }
        return null;
    }

    @Override
    @JsonValue
    public String toString() {
        return this.name;
    }
}
