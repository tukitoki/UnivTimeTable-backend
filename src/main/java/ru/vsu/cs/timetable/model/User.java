package ru.vsu.cs.timetable.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnTransformer;
import ru.vsu.cs.timetable.model.enums.UserRole;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "system_user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @NotNull
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @NotNull
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;
    @NotNull
    @Column(name = "city", nullable = false)
    private String city;
    @NotNull
    @ColumnTransformer(read = "UPPER(role)", write = "LOWER(?)")
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;
    @ManyToOne
    @JoinColumn(name = "university_id", nullable = true)
    private University university;
    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = true)
    private Faculty faculty;
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = true)
    private Group group;
}
