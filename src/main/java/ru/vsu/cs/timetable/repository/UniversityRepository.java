package ru.vsu.cs.timetable.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.timetable.model.University;

public interface UniversityRepository extends CrudRepository<University, Long> {
}
