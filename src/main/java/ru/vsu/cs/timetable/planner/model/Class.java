package ru.vsu.cs.timetable.planner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import ru.vsu.cs.timetable.entity.Equipment;
import ru.vsu.cs.timetable.entity.Group;
import ru.vsu.cs.timetable.entity.User;
import ru.vsu.cs.timetable.entity.enums.TypeClass;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@PlanningEntity
public class Class {

    @PlanningId
    private Long id;
    private String subjectName;
    @PlanningVariable
    private Timeslot timeslot;
    @PlanningVariable
    private Audience audience;
    private TypeClass typeClass;
    private User lecturer;
    private List<Timeslot> impossibleTimes;
    private Set<Group> groups;
    private Set<Equipment> requiredEquipments;
}
