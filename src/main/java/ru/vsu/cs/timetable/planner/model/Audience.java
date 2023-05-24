package ru.vsu.cs.timetable.planner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.entity.Equipment;

import java.util.Set;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Audience {

    private Integer audienceNumber;
    private Long capacity;
    private Set<Equipment> equipments;
}
