package ru.vsu.cs.timetable.logic.planner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.enums.DayOfWeekEnum;
import ru.vsu.cs.timetable.model.enums.WeekType;

import java.time.LocalTime;
import java.util.Objects;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Timeslot {

    private DayOfWeekEnum dayOfWeekEnum;
    private WeekType weekType;
    private LocalTime startTime;
    private LocalTime endTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timeslot timeslot = (Timeslot) o;
        return dayOfWeekEnum == timeslot.dayOfWeekEnum && Objects.equals(startTime, timeslot.startTime) && Objects.equals(endTime, timeslot.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayOfWeekEnum, startTime, endTime);
    }

    @Override
    public String toString() {
        return "Временной промежуток:" +
                " " + dayOfWeekEnum +
                ", " + weekType +
                ", " + startTime + "-" + endTime;
    }
}
