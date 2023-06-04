package ru.vsu.cs.timetable.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.entity.enums.UserRole;

@Setter
@Getter
@AllArgsConstructor
@SuperBuilder
public class UserDto {

    private Long id;
    @NotNull
    private UserRole role;
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
    @Size(min = 5)
    @Size(max = 25)
    @NotNull
    @NotBlank
    private String password;
    private Long universityId;
    private Long facultyId;
    private Long groupId;
}
