package ru.vsu.cs.timetable.planner;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;
import ru.vsu.cs.timetable.entity.Group;
import ru.vsu.cs.timetable.planner.model.Class;

public class TimetableConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                audienceConflict(constraintFactory),
                lecturerConflict(constraintFactory),
                audienceCapacityConflict(constraintFactory),
                studentGroupConflict(constraintFactory),
                audienceEquipmentConflict(constraintFactory),
                timeConflict(constraintFactory)
        };
    }

    private Constraint audienceConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Class.class)
                .join(Class.class,
                        Joiners.equal(Class::getAudience),
                        Joiners.equal(Class::getTimeslot),
                        Joiners.lessThan(Class::getId))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Audience conflict");
    }

    private Constraint audienceCapacityConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Class.class)
                .filter(aClass -> aClass.getAudience().getCapacity() <
                        aClass.getGroups().stream()
                                .mapToInt(Group::getStudentsAmount)
                                .sum()
                        && !aClass.getAudience().getEquipments().containsAll(aClass.getRequiredEquipments()))
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Audience capacity conflict");
    }

    private Constraint audienceEquipmentConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Class.class)
                .filter(aClass ->
                        !aClass.getAudience().getEquipments().containsAll(aClass.getRequiredEquipments()))
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Audience equipment conflict");
    }

    private Constraint lecturerConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Class.class)
                .join(Class.class,
                        Joiners.equal(Class::getTimeslot),
                        Joiners.equal(Class::getLecturer),
                        Joiners.lessThan(Class::getId))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Lecturer conflict");
    }

    private Constraint timeConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Class.class)
                .filter(aClass -> aClass.getImpossibleTimes().contains(aClass.getTimeslot()))
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Time conflict");
    }

    private Constraint studentGroupConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Class.class)
                .join(Class.class,
                        Joiners.equal(Class::getGroups),
                        Joiners.equal(Class::getTimeslot),
                        Joiners.lessThan(Class::getId))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Student group conflict");
    }
}
