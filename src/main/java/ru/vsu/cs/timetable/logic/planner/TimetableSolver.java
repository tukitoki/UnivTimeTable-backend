package ru.vsu.cs.timetable.logic.planner;

import lombok.RequiredArgsConstructor;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.springframework.stereotype.Component;
import ru.vsu.cs.timetable.logic.planner.model.PlanningTimetable;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Component
public class TimetableSolver {

    private final SolverManager<PlanningTimetable, UUID> solverManager;

    public PlanningTimetable solve(PlanningTimetable problem) {
        UUID problemId = UUID.randomUUID();

        SolverJob<PlanningTimetable, UUID> solverJob = solverManager.solve(problemId, problem);
        PlanningTimetable solution;
        try {
            solution = solverJob.getFinalBestSolution();
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException("Solving failed.", e);
        }
        return solution;
    }
}
