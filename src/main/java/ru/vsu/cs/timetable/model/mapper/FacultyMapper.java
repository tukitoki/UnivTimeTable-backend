package ru.vsu.cs.timetable.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vsu.cs.timetable.model.dto.faculty.FacultyDto;
import ru.vsu.cs.timetable.model.dto.faculty.FacultyResponse;
import ru.vsu.cs.timetable.model.dto.group.GroupDto;
import ru.vsu.cs.timetable.model.entity.Faculty;

import java.util.List;

@RequiredArgsConstructor
@Component
public class FacultyMapper {

    private final GroupMapper groupMapper;

    public FacultyDto toDto(Faculty faculty) {
        return FacultyDto.builder()
                .id(faculty.getId())
                .name(faculty.getName())
                .build();
    }

    public FacultyResponse toResponse(Faculty faculty) {
        List<GroupDto> groups = faculty.getGroups()
                .stream()
                .filter(group -> group.getHeadmanId() == null)
                .map(groupMapper::toDto)
                .toList();

        return FacultyResponse.builder()
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
