package ru.vsu.cs.timetable.planner.model;

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
public class Timetable {

    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<Timeslot> timeslots;
    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<Audience> audiences;
    @PlanningEntityCollectionProperty
    private List<Class> classes;
    @PlanningScore
    private HardSoftScore hardSoftScore;
}
