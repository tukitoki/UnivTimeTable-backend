package ru.vsu.cs.timetable.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.entity.enums.UserRole;

@Setter
@Getter
@AllArgsConstructor
@SuperBuilder
@Schema(description = "Информация о пользователе")
public class UserResponse {

    @Schema(description = "Id пользователя", example = "1")
    private Long id;
    @Schema(description = "Роль пользователя", example = "1")
    private UserRole role;
    @Schema(description = "Полное имя пользовател", example = "Андреев Андрей Андреевич")
    private String fullName;
    @Schema(description = "Город", example = "Воронеж")
    private String city;
    @Schema(description = "Название университета", example = "Воронежский государственный университет")
    private String univName;
    @Schema(description = "Название факультета", example = "Факультет компьютерных наук")
    private String facultyName;
    @Schema(description = "Номер группы", example = "1")
    private Integer group;
}
