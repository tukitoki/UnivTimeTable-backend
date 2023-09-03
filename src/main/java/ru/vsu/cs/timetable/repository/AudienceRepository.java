package ru.vsu.cs.timetable.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.timetable.model.entity.Audience;
import ru.vsu.cs.timetable.model.entity.Faculty;
import ru.vsu.cs.timetable.model.entity.University;

import java.util.List;
import java.util.Optional;

public interface AudienceRepository extends JpaRepository<Audience, Long> {

    Optional<Audience> findByAudienceNumberAndUniversityAndFaculty(@NotNull Integer audienceNumber,
                                                                   @NotNull University university,
                                                                   @NotNull Faculty faculty);

    Optional<Audience> findByAudienceNumberAndFaculty(@NotNull Integer audienceNumber, Faculty faculty);

    List<Audience> findAllByFaculty(Faculty faculty);
}
