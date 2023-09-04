package ru.vsu.cs.timetable.logic.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import ru.vsu.cs.timetable.model.dto.page.SortDirection;
import ru.vsu.cs.timetable.model.dto.university.UniversityDto;
import ru.vsu.cs.timetable.model.dto.university.UniversityPageDto;
import ru.vsu.cs.timetable.model.entity.University;

import java.util.List;

@Validated
public interface UniversityService {

    List<UniversityDto> getAllUniversitiesV2(String universityName, SortDirection order);

    UniversityPageDto getAllUniversities(int currentPage, int pageSize,
                                         String universityName, SortDirection order);

    UniversityDto getUniversityById(@NotNull Long id);

    University findUnivById(@NotNull Long id);

    University findUnivByName(@NotNull String name);

    List<University> findAllUniversities();

    void createUniversity(@NotNull @Valid UniversityDto createUnivRequest);

    void updateUniversity(@NotNull @Valid UniversityDto universityDto,
                          @NotNull Long id);

    void deleteUniversity(@NotNull Long id);
}
