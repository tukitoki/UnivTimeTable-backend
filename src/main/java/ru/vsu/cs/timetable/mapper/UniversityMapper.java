package ru.vsu.cs.timetable.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vsu.cs.timetable.dto.faculty.FacultyDto;
import ru.vsu.cs.timetable.dto.university.UniversityDto;
import ru.vsu.cs.timetable.entity.University;

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
                .faculties(faculties)
                .build();
    }

    public University toEntity(UniversityDto univDto) {
        return University.builder()
                .id(univDto.getId())
                .name(univDto.getUnivName())
                .city(univDto.getCity())
                .build();
    }
}
