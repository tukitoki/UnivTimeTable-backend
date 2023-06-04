package ru.vsu.cs.timetable.model.dto.faculty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.dto.group.GroupDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class FacultyResponse {

    private Long id;
    @NotNull
    @NotBlank
    private String name;
    private List<GroupDto> groups;
}
