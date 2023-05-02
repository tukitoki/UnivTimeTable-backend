package ru.vsu.cs.timetable.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.timetable.model.Faculty;

public interface FacultyRepository extends CrudRepository<Faculty, Long> {
}
