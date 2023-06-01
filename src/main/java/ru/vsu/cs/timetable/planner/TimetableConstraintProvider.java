package ru.vsu.cs.timetable.planner;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;
import ru.vsu.cs.timetable.entity.Group;
import ru.vsu.cs.timetable.planner.model.PlanningClass;

import java.time.LocalTime;

public class TimetableConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                audienceConflict(constraintFactory),
                lecturerConflict(constraintFactory),
                audienceCapacityConflict(constraintFactory),
                studentGroupConflict(constraintFactory),
                audienceEquipmentConflict(constraintFactory),
                impossibleTimeConflict(constraintFactory),
                timeConflict(constraintFactory)
        };
    }

    private Constraint audienceConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(PlanningClass.class)
                .join(PlanningClass.class,
                        Joiners.equal(PlanningClass::getAudience),
                        Joiners.equal(PlanningClass::getTimeslot),
                        Joiners.lessThan(PlanningClass::getId))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Audience conflict");
    }

    private Constraint audienceCapacityConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(PlanningClass.class)
                .filter(aClass ->
                        aClass.getAudience().getCapacity() < aClass.getGroups().stream()
                                .mapToInt(Group::getStudentsAmount)
                                .sum()
                        && !aClass.getAudience().getEquipments().containsAll(aClass.getRequiredEquipments()))
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Audience capacity conflict");
    }

    private Constraint audienceEquipmentConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(PlanningClass.class)
                .filter(aClass ->
                        !aClass.getAudience().getEquipments().containsAll(aClass.getRequiredEquipments()))
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Audience equipment conflict");
    }

    private Constraint lecturerConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(PlanningClass.class)
                .join(PlanningClass.class,
                        Joiners.equal(PlanningClass::getTimeslot),
                        Joiners.equal(PlanningClass::getLecturer),
                        Joiners.lessThan(PlanningClass::getId))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Lecturer conflict");
    }

    private Constraint impossibleTimeConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(PlanningClass.class)
                .filter(aClass -> aClass.getImpossibleTimes().contains(aClass.getTimeslot()))
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Impossible time conflict");
    }

    private Constraint studentGroupConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(PlanningClass.class)
                .join(PlanningClass.class,
                        Joiners.equal(PlanningClass::getGroups),
                        Joiners.equal(PlanningClass::getTimeslot),
                        Joiners.lessThan(PlanningClass::getId))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Student group conflict");
    }

    private Constraint timeConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(PlanningClass.class)
                .filter(aClass -> aClass.getTimeslot().getStartTime().isAfter(LocalTime.of(16, 45)))
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Time conflict before 16:45");
    }
}
