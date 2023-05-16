package ru.vsu.cs.timetable.service;

import ru.vsu.cs.timetable.dto.page.SortDirection;
import ru.vsu.cs.timetable.dto.university.CreateUnivRequest;
import ru.vsu.cs.timetable.dto.university.UniversityDto;
import ru.vsu.cs.timetable.dto.university.UniversityPageDto;
import ru.vsu.cs.timetable.model.University;

import java.util.List;

public interface UniversityService {

    UniversityPageDto getAllUniversities(int pageNumber, int pageSize,
                                         String universityName, SortDirection order);

    UniversityDto getUniversityById(Long id);

    University findUnivById(Long id);

    University findUnivByName(String name);

    List<University> findAllUniversities();

    void createUniversity(CreateUnivRequest createUnivRequest);

    void updateUniversity(UniversityDto universityDto, Long id);

    void deleteUniversity(Long id);
}
