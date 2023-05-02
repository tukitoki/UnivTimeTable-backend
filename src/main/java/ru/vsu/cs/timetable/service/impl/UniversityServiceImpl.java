package ru.vsu.cs.timetable.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.cs.timetable.dto.page.SortDirection;
import ru.vsu.cs.timetable.dto.university.CreateUnivRequest;
import ru.vsu.cs.timetable.dto.university.UniversityDto;
import ru.vsu.cs.timetable.dto.university.UniversityPageDto;
import ru.vsu.cs.timetable.exception.UniversityException;
import ru.vsu.cs.timetable.model.University;
import ru.vsu.cs.timetable.repository.UniversityRepository;
import ru.vsu.cs.timetable.service.UniversityService;

@RequiredArgsConstructor
@Service
public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepository universityRepository;

    @Override
    public UniversityPageDto getAllUniversities(int pageNumber, int pageSize,
                                                String universityName, SortDirection order) {
        return null;
    }

    @Override
    public University findUnivById(Long id) {
        return universityRepository.findById(id)
                .orElseThrow(UniversityException.CODE.ID_NOT_FOUND::get);
    }

    @Override
    public void createUniversity(CreateUnivRequest createUnivRequest) {
        if (universityRepository.findByName(createUnivRequest.getUniversityName()).isPresent()) {
            throw UniversityException.CODE.UNIVERSITY_NAME_NOT_FOUND.get();
        }

        University university = University.builder()
                .name(createUnivRequest.getUniversityName())
                .city(createUnivRequest.getCity())
                .build();

        universityRepository.save(university);
    }

    @Override
    public void updateUniversity(UniversityDto universityDto, Long id) {

    }

    @Override
    public void deleteUniversity(Long id) {

    }
}
