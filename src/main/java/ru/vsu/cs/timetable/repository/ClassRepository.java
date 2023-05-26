package ru.vsu.cs.timetable.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.timetable.entity.Class;
import ru.vsu.cs.timetable.entity.Group;
import ru.vsu.cs.timetable.entity.User;
import ru.vsu.cs.timetable.entity.enums.DayOfWeekEnum;
import ru.vsu.cs.timetable.entity.enums.WeekType;

import java.util.List;

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
}
