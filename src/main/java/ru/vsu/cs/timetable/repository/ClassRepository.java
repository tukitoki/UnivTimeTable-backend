package ru.vsu.cs.timetable.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.timetable.entity.Class;

public interface ClassRepository extends CrudRepository<Class, Long> {
}
