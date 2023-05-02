package ru.vsu.cs.timetable.service;

import ru.vsu.cs.timetable.dto.page.SortDirection;
import ru.vsu.cs.timetable.dto.university.CreateUnivRequest;
import ru.vsu.cs.timetable.dto.university.UniversityDto;
import ru.vsu.cs.timetable.dto.university.UniversityPageDto;
import ru.vsu.cs.timetable.model.University;

public interface UniversityService {

    UniversityPageDto getAllUniversities(int pageNumber, int pageSize,
                                         String universityName, SortDirection order);

    University findUnivById(Long id);

    void createUniversity(CreateUnivRequest createUnivRequest);

    void updateUniversity(UniversityDto universityDto, Long id);

    void deleteUniversity(Long id);
}
