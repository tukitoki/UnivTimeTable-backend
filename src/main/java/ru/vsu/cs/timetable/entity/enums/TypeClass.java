package ru.vsu.cs.timetable.entity.enums;

public enum TypeClass {
    LECTURE("Лекция"),
    SEMINAR("Семинар")
    ;

    private final String name;

    TypeClass(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
