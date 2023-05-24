package ru.vsu.cs.timetable.planner;

import lombok.RequiredArgsConstructor;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.springframework.stereotype.Component;
import ru.vsu.cs.timetable.planner.model.Timetable;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Component
public class TimetableSolver {

    private final SolverManager<Timetable, UUID> solverManager;

    public Timetable solve(Timetable problem) {
        UUID problemId = UUID.randomUUID();

        SolverJob<Timetable, UUID> solverJob = solverManager.solve(problemId, problem);
        Timetable solution;
        try {
            solution = solverJob.getFinalBestSolution();
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException("Solving failed.", e);
        }
        return solution;
    }
}
