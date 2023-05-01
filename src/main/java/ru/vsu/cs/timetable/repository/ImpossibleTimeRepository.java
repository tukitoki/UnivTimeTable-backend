package ru.vsu.cs.timetable.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.timetable.model.ImpossibleTime;
import ru.vsu.cs.timetable.model.ImpossibleTimeId;

public interface ImpossibleTimeRepository extends CrudRepository<ImpossibleTime, ImpossibleTimeId> {
}
