package ru.vsu.cs.timetable.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.timetable.model.Audience;

public interface AudienceRepository extends CrudRepository<Audience, Long> {
}
