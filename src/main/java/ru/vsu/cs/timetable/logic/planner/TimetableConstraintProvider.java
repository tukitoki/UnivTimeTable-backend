package ru.vsu.cs.timetable.logic.planner;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;
import ru.vsu.cs.timetable.model.entity.Group;
import ru.vsu.cs.timetable.logic.planner.model.PlanningClass;
import ru.vsu.cs.timetable.logic.planner.utils.HardViolationTemplateUtil;

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
                .filter((planningClass, planningClass2) -> {
                    boolean violation = planningClass.getAudience().equals(planningClass2.getAudience());
                    if (violation) {
                        planningClass.setHardViolation(HardViolationTemplateUtil.audienceConflict(planningClass, planningClass2));
                    }
                    return violation;
                })
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Audience conflict");
    }

    private Constraint audienceCapacityConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(PlanningClass.class)
                .filter(aClass ->
                        aClass.getAudience().getCapacity() < aClass.getGroups().stream()
                                .mapToInt(Group::getStudentsAmount)
                                .sum()
                )
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Audience capacity conflict");
    }

    private Constraint audienceEquipmentConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(PlanningClass.class)
                .filter(planningClass ->
                        !planningClass.getAudience().getEquipments()
                                .containsAll(planningClass.getRequiredEquipments()))
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Audience equipment conflict");
    }

    private Constraint lecturerConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(PlanningClass.class)
                .join(PlanningClass.class,
                        Joiners.equal(PlanningClass::getTimeslot),
                        Joiners.equal(PlanningClass::getLecturer),
                        Joiners.lessThan(PlanningClass::getId))
                .filter((planningClass, planningClass2) -> {
                    boolean violation = planningClass.getLecturer().equals(planningClass2.getLecturer());
                    if (violation) {
                        planningClass.setHardViolation(HardViolationTemplateUtil.lecturerConflict(planningClass, planningClass2));
                    }
                    return violation;
                })
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Lecturer conflict");
    }

    private Constraint impossibleTimeConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(PlanningClass.class)
                .filter(planningClass -> planningClass.getImpossibleTimes()
                        .contains(planningClass.getTimeslot()))
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Impossible time conflict");
    }

    private Constraint studentGroupConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(PlanningClass.class)
                .join(PlanningClass.class,
                        Joiners.equal(PlanningClass::getGroups),
                        Joiners.equal(PlanningClass::getTimeslot),
                        Joiners.lessThan(PlanningClass::getId))
                .filter((planningClass, planningClass2) -> {
                    boolean violation = planningClass.getGroups().equals(planningClass2.getGroups());
                    if (violation) {
                        planningClass.setHardViolation(HardViolationTemplateUtil.studentGroupConflict(planningClass, planningClass2));
                    }
                    return violation;
                })
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Student group conflict");
    }

    private Constraint timeConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(PlanningClass.class)
                .filter(planningClass -> planningClass.getTimeslot()
                        .getStartTime().isAfter(LocalTime.of(16, 45)))
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Time conflict before 16:45");
    }
}
