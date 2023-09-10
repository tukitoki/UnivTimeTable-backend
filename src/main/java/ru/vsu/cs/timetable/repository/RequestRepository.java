package ru.vsu.cs.timetable.repository;

import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.timetable.model.entity.Faculty;
import ru.vsu.cs.timetable.model.entity.Request;
import ru.vsu.cs.timetable.model.entity.User;

import java.util.List;

public interface RequestRepository extends CrudRepository<Request, Long> {

    @NonNull
    List<Request> findAll();

    List<Request> findAllByGroupFacultyOrderByTypeClass(@NotNull Faculty group_faculty);

    List<Request> findAllByLecturer(@NotNull User lecturer);
}
