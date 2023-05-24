package ru.vsu.cs.timetable.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.timetable.entity.Audience;
import ru.vsu.cs.timetable.entity.Faculty;
import ru.vsu.cs.timetable.entity.University;

import java.util.List;
import java.util.Optional;

public interface AudienceRepository extends CrudRepository<Audience, Long> {

    Optional<Audience> findByAudienceNumberAndUniversityAndFaculty(@NotNull Integer audienceNumber,
                                                                   @NotNull University university,
                                                                   @NotNull Faculty faculty);

    List<Audience> findAllByFaculty(Faculty faculty);
}
