package ru.vsu.cs.timetable.logic.planner;

import org.optaplanner.core.api.domain.constraintweight.ConstraintWeight;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;
import ru.vsu.cs.timetable.logic.planner.model.PlanningClass;
import ru.vsu.cs.timetable.logic.planner.utils.HardViolationTemplateUtil;
import ru.vsu.cs.timetable.model.entity.Group;

import java.time.Duration;
import java.time.LocalTime;

public class TimetableConstraintProvider implements ConstraintProvider {

    @ConstraintWeight("FirstSoftScoreConstraint")
    private final static HardSoftScore FIRST_SOFT_SCORE = HardSoftScore.ofSoft(1);
    @ConstraintWeight("SecondSoftScoreConstraint")
    private final static HardSoftScore SECOND_SOFT_SCORE = HardSoftScore.ofSoft(2);

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                audienceConflict(constraintFactory),
                lecturerConflict(constraintFactory),
                audienceCapacityRequired(constraintFactory),
                studentGroupConflict(constraintFactory),
                audienceEquipmentRequired(constraintFactory),
                preferredImpossibleTime(constraintFactory),
                preferredTime(constraintFactory),
                studentGroupSubjectVariety(constraintFactory)
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

    private Constraint audienceCapacityRequired(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(PlanningClass.class)
                .filter(aClass ->
                        aClass.getAudience().getCapacity() < aClass.getGroups().stream()
                                .mapToInt(Group::getStudentsAmount)
                                .sum()
                )
                .penalize(HardSoftScore.ONE_SOFT.multiply(SECOND_SOFT_SCORE.softScore()))
                .asConstraint("Audience capacity required");
    }

    private Constraint audienceEquipmentRequired(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(PlanningClass.class)
                .filter(planningClass ->
                        !planningClass.getAudience().getEquipments()
                                .containsAll(planningClass.getRequiredEquipments()))
                .penalize(HardSoftScore.ONE_SOFT.multiply(FIRST_SOFT_SCORE.softScore()))
                .asConstraint("Audience equipment required");
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

    private Constraint preferredImpossibleTime(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(PlanningClass.class)
                .filter(planningClass -> planningClass.getImpossibleTimes()
                        .contains(planningClass.getTimeslot()))
                .penalize(HardSoftScore.ONE_SOFT.multiply(SECOND_SOFT_SCORE.softScore()))
                .asConstraint("Preferred Impossible time");
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

    private Constraint studentGroupSubjectVariety(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(PlanningClass.class)
                .join(PlanningClass.class,
                        Joiners.equal(PlanningClass::getSubjectName),
                        Joiners.equal(PlanningClass::getGroups),
                        Joiners.equal(planningClass -> planningClass.getTimeslot().getDayOfWeekEnum()),
                        Joiners.equal(planningClass -> planningClass.getTimeslot().getWeekType()))
                .filter((planningClass, planningClass2) -> {
                    Duration between = Duration.between(planningClass.getTimeslot().getEndTime(),
                            planningClass2.getTimeslot().getStartTime());
                    return !between.isNegative() && between.compareTo(Duration.ofMinutes(30)) <= 0;
                })
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Student group subject variety");
    }

    private Constraint preferredTime(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(PlanningClass.class)
                .filter(planningClass -> planningClass.getTimeslot()
                        .getStartTime().isAfter(LocalTime.of(13, 25)))
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Preferred Time are before 13:25");
    }
}
