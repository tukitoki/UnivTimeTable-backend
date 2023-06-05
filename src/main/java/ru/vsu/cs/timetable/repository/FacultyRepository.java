package ru.vsu.cs.timetable.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.timetable.model.entity.Faculty;
import ru.vsu.cs.timetable.model.entity.University;

import java.util.List;
import java.util.Optional;

public interface FacultyRepository extends CrudRepository<Faculty, Long> {

    Optional<Faculty> findByNameAndUniversity(@NotNull String name, University university);

    List<Faculty> findAllByUniversity_Id(Long university_id);
}
