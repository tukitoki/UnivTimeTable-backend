package ru.vsu.cs.timetable.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.timetable.model.Group;

public interface GroupRepository extends CrudRepository<Group, Long> {
}
