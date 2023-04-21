package ru.vsu.cs.timetable.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "university")
@Entity
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String city;
    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL)
    private List<User> users;
    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL)
    private List<Faculty> faculties;
    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL)
    private List<Audience> audiences;
}
