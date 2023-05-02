package ru.vsu.cs.timetable.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "university_group")
@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    @Column(name = "students_amount", nullable = false)
    private Integer studentsAmount;
    @NotNull
    @Column(name = "course_number", nullable = false)
    private Integer courseNumber;
    @NotNull
    @Column(name = "group_number", nullable = false)
    private Integer groupNumber;
    @Column(name = "headman_id", nullable = true)
    private Long headmanId;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;
    @OneToMany(mappedBy = "group")
    private List<User> users;
    @ManyToMany
    @JoinTable(name = "group_class",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "class_id"))
    private Set<Class> classes;
}
