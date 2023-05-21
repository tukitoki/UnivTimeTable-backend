package ru.vsu.cs.timetable.repository;

import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.timetable.entity.University;

import java.util.List;
import java.util.Optional;

public interface UniversityRepository extends CrudRepository<University, Long> {

    Optional<University> findByName(@NotNull String name);

    @NonNull
    List<University> findAll();

    Optional<University> findByNameIgnoreCase(@NotNull String name);
}
