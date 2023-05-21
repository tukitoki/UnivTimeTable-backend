package ru.vsu.cs.timetable.entity.enums;

public enum DayOfWeekEnum {
    MONDAY("Понедельник"),
    TUESDAY("Вторник"),
    WEDNESDAY("Среда"),
    THURSDAY("Четверг"),
    FRIDAY("Пятница"),
    SATURDAY("Суббота"),
    SUNDAY("Воскресенье")
    ;

    private final String name;

    DayOfWeekEnum(String name) {
        this.name = name;
    }

    public static DayOfWeekEnum fromName(String name) {
        for (var dayOfWeek : DayOfWeekEnum.values()) {
            if (dayOfWeek.name.equalsIgnoreCase(name)) {
                return dayOfWeek;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
