package ru.vsu.cs.timetable.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.cs.timetable.dto.faculty.CreateFacultyRequest;
import ru.vsu.cs.timetable.dto.faculty.FacultyDto;
import ru.vsu.cs.timetable.dto.faculty.FacultyPageDto;
import ru.vsu.cs.timetable.dto.page.SortDirection;
import ru.vsu.cs.timetable.exception.FacultyException;
import ru.vsu.cs.timetable.model.Faculty;
import ru.vsu.cs.timetable.model.University;
import ru.vsu.cs.timetable.repository.FacultyRepository;
import ru.vsu.cs.timetable.service.FacultyService;
import ru.vsu.cs.timetable.service.UniversityService;

@RequiredArgsConstructor
@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final UniversityService universityService;
    @Override
    public FacultyPageDto getFacultiesByUniversity(int pageNumber, int pageSize, String name,
                                                   SortDirection order, Long univId) {
        return null;
    }

    @Override
    public Faculty findFacultyById(Long id) {
        return facultyRepository.findById(id)
                .orElseThrow(FacultyException.CODE.ID_NOT_FOUND::get);
    }

    @Override
    public void createFaculty(CreateFacultyRequest createFacultyRequest, Long univId) {
        University university = universityService.findUnivById(univId);

        if (facultyRepository.findByName(createFacultyRequest.getName()).isPresent()) {
            throw FacultyException.CODE.NAME_ALREADY_PRESENT.get();
        }

        Faculty faculty = Faculty.builder()
                .name(createFacultyRequest.getName())
                .university(university)
                .build();

        facultyRepository.save(faculty);
    }

    @Override
    public void deleteFaculty(Long id) {

    }

    @Override
    public void updateFaculty(FacultyDto facultyDto, Long id) {

    }
}
