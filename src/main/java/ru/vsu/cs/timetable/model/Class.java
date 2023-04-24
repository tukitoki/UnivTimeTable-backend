package ru.vsu.cs.timetable.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.enums.TypeClass;

import java.sql.Time;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "class")
@Entity
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String subjectName;
    @NotNull
    private Long lecturerId;
    @NotNull
    private Time startTime;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TypeClass typeClass;
    @ManyToOne
    @JoinColumn(name = "audience_id")
    private Audience audience;
    @ManyToOne
    @JoinColumn(name = "day_of_week_id")
    private DayOfWeek dayOfWeek;
    @ManyToMany(mappedBy = "classes")
    private Set<Group> groups;
    @ManyToOne
    @JoinColumn(name = "timetable_id")
    private Timetable timetable;
}
