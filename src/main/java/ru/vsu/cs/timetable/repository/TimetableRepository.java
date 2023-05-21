package ru.vsu.cs.timetable.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.timetable.entity.Timetable;

public interface TimetableRepository extends CrudRepository<Timetable, Long> {
}
