package ru.vsu.cs.timetable.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.timetable.model.Faculty;

import java.util.Optional;

public interface FacultyRepository extends CrudRepository<Faculty, Long> {

    Optional<Faculty> findByName(@NotNull String name);
}
