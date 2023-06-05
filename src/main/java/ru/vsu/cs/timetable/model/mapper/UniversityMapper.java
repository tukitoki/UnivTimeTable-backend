package ru.vsu.cs.timetable.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vsu.cs.timetable.model.dto.faculty.FacultyResponse;
import ru.vsu.cs.timetable.model.dto.university.UniversityDto;
import ru.vsu.cs.timetable.model.dto.university.UniversityResponse;
import ru.vsu.cs.timetable.model.entity.University;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UniversityMapper {

    private final FacultyMapper facultyMapper;

    public UniversityDto toDto(University university) {
        return UniversityDto.builder()
                .id(university.getId())
                .universityName(university.getName())
                .city(university.getCity())
                .build();
    }

    public University toEntity(UniversityDto univDto) {
        return University.builder()
                .id(univDto.getId())
                .name(univDto.getUniversityName())
                .city(univDto.getCity())
                .build();
    }

    public UniversityResponse toResponse(University university) {
        List<FacultyResponse> facultyResponses = university.getFaculties()
                .stream()
                .map(facultyMapper::toResponse)
                .toList();

        return UniversityResponse.builder()
                .id(university.getId())
                .city(university.getName())
                .universityName(university.getName())
                .facultyDtos(facultyResponses)
                .build();
    }
}
