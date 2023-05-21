package ru.vsu.cs.timetable.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vsu.cs.timetable.dto.faculty.FacultyDto;
import ru.vsu.cs.timetable.dto.group.GroupDto;
import ru.vsu.cs.timetable.entity.Faculty;

import java.util.List;

@RequiredArgsConstructor
@Component
public class FacultyMapper {

    private final GroupMapper groupMapper;

    public FacultyDto toDto(Faculty faculty) {
        List<GroupDto> groups = faculty.getGroups()
                .stream()
                .map(groupMapper::toDto)
                .toList();

        return FacultyDto.builder()
                .id(faculty.getId())
                .name(faculty.getName())
                .groups(groups)
                .build();
    }

    public Faculty toEntity(FacultyDto facultyDto) {
        return Faculty.builder()
                .id(facultyDto.getId())
                .name(facultyDto.getName())
                .build();
    }
}
