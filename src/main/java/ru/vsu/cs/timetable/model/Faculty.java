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
@Table(name = "faculty")
@Entity
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false)
    private University university;
    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
    private List<Group> groups;
    @OneToMany(mappedBy = "faculty")
    private List<Audience> audiences;
}
