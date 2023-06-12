package ru.vsu.cs.timetable.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.timetable.model.entity.Timetable;

import java.util.List;

public interface TimetableRepository extends CrudRepository<Timetable, Long> {

    List<Timetable> findAllByClassesEmpty();
}
