package ru.vsu.cs.timetable.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vsu.cs.timetable.dto.faculty.FacultyDto;
import ru.vsu.cs.timetable.dto.university.UniversityDto;
import ru.vsu.cs.timetable.model.University;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UniversityMapper {

    private final FacultyMapper facultyMapper;

    public UniversityDto toDto(University university) {
        List<FacultyDto> faculties = university.getFaculties()
                .stream()
                .map(facultyMapper::toDto)
                .toList();

        return UniversityDto.builder()
                .id(university.getId())
                .univName(university.getName())
                .city(university.getCity())
                .faculites(faculties)
                .build();
    }
}
