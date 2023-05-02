package ru.vsu.cs.timetable.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.timetable.model.University;

import java.util.Optional;

public interface UniversityRepository extends CrudRepository<University, Long> {

    Optional<University> findByName(@NotNull String name);
}
