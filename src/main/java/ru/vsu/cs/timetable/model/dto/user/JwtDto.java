package ru.vsu.cs.timetable.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtDto {

    private String accessToken;
    private String refreshToken;
}
