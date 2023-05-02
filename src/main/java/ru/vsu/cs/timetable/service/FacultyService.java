package ru.vsu.cs.timetable.service;

import ru.vsu.cs.timetable.dto.faculty.CreateFacultyRequest;
import ru.vsu.cs.timetable.dto.faculty.FacultyDto;
import ru.vsu.cs.timetable.dto.faculty.FacultyPageDto;
import ru.vsu.cs.timetable.dto.page.SortDirection;
import ru.vsu.cs.timetable.model.Faculty;

public interface FacultyService {

    FacultyPageDto getFacultiesByUniversity(int pageNumber, int pageSize, String name,
                                            SortDirection order, Long univId);

    Faculty findFacultyById(Long id);

    void createFaculty(CreateFacultyRequest createFacultyRequest, Long univId);

    void deleteFaculty(Long id);

    void updateFaculty(FacultyDto facultyDto, Long id);
}
