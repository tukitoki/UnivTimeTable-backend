package ru.vsu.cs.timetable.logic.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import ru.vsu.cs.timetable.model.dto.faculty.CreateFacultyRequest;
import ru.vsu.cs.timetable.model.dto.faculty.FacultyDto;
import ru.vsu.cs.timetable.model.dto.faculty.FacultyPageDto;
import ru.vsu.cs.timetable.model.dto.page.SortDirection;
import ru.vsu.cs.timetable.model.entity.Faculty;

import java.util.List;

@Validated
public interface FacultyService {

    FacultyPageDto getFacultiesByUniversity(int currentPage, int pageSize, String name,
                                            SortDirection order, @NotNull Long univId);

    List<FacultyDto> getFacultiesByUniversity(@NotNull Long univId);

    FacultyDto getFacultyById(@NotNull Long id);

    Faculty findFacultyById(@NotNull Long id);

    void createFaculty(@NotNull @Valid CreateFacultyRequest createFacultyRequest,
                       @NotNull Long univId);

    void deleteFaculty(@NotNull Long id);

    void updateFaculty(@NotNull @Valid FacultyDto facultyDto,
                       @NotNull Long id);
}
