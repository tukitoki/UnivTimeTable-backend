package ru.vsu.cs.timetable.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.timetable.model.Class;

public interface ClassRepository extends CrudRepository<Class, Long> {
}
