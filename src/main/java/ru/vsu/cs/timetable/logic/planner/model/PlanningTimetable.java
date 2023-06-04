package ru.vsu.cs.timetable.logic.planner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.List;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@PlanningSolution
public class PlanningTimetable {

    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<Timeslot> timeslots;
    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<PlanningAudience> audiences;
    @PlanningEntityCollectionProperty
    private List<PlanningClass> classes;
    @PlanningScore
    private HardSoftScore hardSoftScore;
}
