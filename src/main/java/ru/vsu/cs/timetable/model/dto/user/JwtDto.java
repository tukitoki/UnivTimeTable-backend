package ru.vsu.cs.timetable.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "AccessToken и RefreshToken")
public class JwtDto {

    private String accessToken;
    private String refreshToken;
}
