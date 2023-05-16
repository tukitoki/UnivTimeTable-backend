package ru.vsu.cs.timetable.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@SuperBuilder
public class UserDto {

    private Long id;
    @NotNull
    @NotBlank
    private String role;
    @NotNull
    @NotBlank
    private String fullName;
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    private String email;
    @NotNull
    @NotBlank
    private String city;
    @NotNull
    @NotBlank
    private String password;
    private Long universityId;
    private Long facultyId;
    private Long groupId;
}
