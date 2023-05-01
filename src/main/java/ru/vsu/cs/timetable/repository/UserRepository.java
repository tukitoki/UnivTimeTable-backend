package ru.vsu.cs.timetable.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.timetable.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
