package ru.vsu.cs.timetable.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.timetable.model.Audience;
import ru.vsu.cs.timetable.model.Faculty;
import ru.vsu.cs.timetable.model.University;

import java.util.Optional;

public interface AudienceRepository extends CrudRepository<Audience, Long> {

    Optional<Audience> findByAudienceNumberAndUniversityAndFaculty(@NotNull Integer audienceNumber,
                                                                   @NotNull University university,
                                                                   @NotNull Faculty faculty);
}
