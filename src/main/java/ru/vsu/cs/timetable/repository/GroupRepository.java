package ru.vsu.cs.timetable.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.timetable.model.Faculty;
import ru.vsu.cs.timetable.model.Group;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends CrudRepository<Group, Long> {

    Optional<Group> findByFacultyAndGroupNumberAndCourseNumber(@NotNull Faculty faculty,
                                                               @NotNull Integer groupNumber,
                                                               @NotNull Integer courseNumber);

    @Query(value = """
            SELECT g.courseNumber
            FROM Group g
            """)
    List<Integer> findAllCourses();
}
