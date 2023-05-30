package ru.vsu.cs.timetable.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.timetable.entity.Class;
import ru.vsu.cs.timetable.entity.Group;
import ru.vsu.cs.timetable.entity.User;
import ru.vsu.cs.timetable.entity.enums.DayOfWeekEnum;
import ru.vsu.cs.timetable.entity.enums.TypeClass;
import ru.vsu.cs.timetable.entity.enums.WeekType;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ClassRepository extends CrudRepository<Class, Long> {

    List<Class> findAllByWeekTypeAndGroupsContainsAndDayOfWeekOrderByStartTimeAsc(@NotNull WeekType weekType,
                                                                                  @NotNull Group group,
                                                                                  @NotNull DayOfWeekEnum dayOfWeek);

    List<Class> findAllByWeekTypeAndLecturerAndDayOfWeekOrderByStartTimeAsc(@NotNull WeekType weekType,
                                                                            @NotNull User lecturer,
                                                                            @NotNull DayOfWeekEnum dayOfWeek);

    List<Class> findAllByGroupsContainsAndDayOfWeek(@NotNull Group group,
                                                    @NotNull DayOfWeekEnum dayOfWeek);

    List<Class> findAllByLecturerAndDayOfWeek(@NotNull User lecturer,
                                              @NotNull DayOfWeekEnum dayOfWeek);

    List<Class> findAllByLecturer(@NotNull User lecturer);

    List<Class> findAllByGroupsContains(@NotNull Group group);

    @Query(value = """
            SELECT c
            FROM Class c
            WHERE c.subjectName = :subjectName AND c.startTime = :startTime
            AND c.audience.audienceNumber = :audience AND c.dayOfWeek = :dayOfWeek
            AND c.typeClass = :typeOfClass AND c.weekType = :weekType AND c.lecturer = :lecturer
            """)
    Optional<Class> findClassToMove(String subjectName, LocalTime startTime, Integer audience,
                                    DayOfWeekEnum dayOfWeek, TypeClass typeOfClass, WeekType weekType,
                                    User lecturer);
}
