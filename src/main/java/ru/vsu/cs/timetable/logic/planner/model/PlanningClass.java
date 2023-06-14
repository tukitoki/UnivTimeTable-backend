package ru.vsu.cs.timetable.logic.planner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import ru.vsu.cs.timetable.model.entity.Equipment;
import ru.vsu.cs.timetable.model.entity.Group;
import ru.vsu.cs.timetable.model.entity.User;
import ru.vsu.cs.timetable.model.enums.TypeClass;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@PlanningEntity
public class PlanningClass {

    @PlanningId
    private Long id;
    private String subjectName;
    @PlanningVariable
    private Timeslot timeslot;
    @PlanningVariable
    private PlanningAudience audience;
    private TypeClass typeClass;
    private User lecturer;
    private List<Timeslot> impossibleTimes;
    private Set<Group> groups;
    private Set<Equipment> requiredEquipments;
    private String hardViolation;

    @Override
    public String toString() {
        return "Пара:" +
                " " + subjectName +
                ", " + timeslot +
                ", " + lecturer.getFullName();
    }
}
