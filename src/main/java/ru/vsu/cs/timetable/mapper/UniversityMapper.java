package ru.vsu.cs.timetable.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vsu.cs.timetable.dto.university.UniversityDto;
import ru.vsu.cs.timetable.entity.University;

@RequiredArgsConstructor
@Component
public class UniversityMapper {

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
}
