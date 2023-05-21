package ru.vsu.cs.timetable.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.timetable.entity.Request;

public interface RequestRepository extends CrudRepository<Request, Long> {
}
