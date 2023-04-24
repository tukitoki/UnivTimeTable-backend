package ru.vsu.cs.timetable.dto.university;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CreateUnivRequest {

    private String universityName;
    private String city;
}
