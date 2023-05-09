package ru.vsu.cs.timetable.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnTransformer;
import ru.vsu.cs.timetable.model.enums.DayOfWeekEnum;
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
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    @Column(name = "subject_name", nullable = false)
    private String subjectName;
    @NotNull
    @Column(name = "start_time", nullable = false)
    private Time startTime;
    @NotNull
    @ColumnTransformer(read = "UPPER(name)", write = "LOWER(?)")
    @Enumerated(EnumType.STRING)
    @Column(name = "type_class", nullable = false)
    private TypeClass typeClass;
    @NotNull
    @ColumnTransformer(read = "UPPER(dayOfWeek)", write = "LOWER(?)")
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeekEnum dayOfWeek;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "lecturer_id", nullable = false)
    private User lecturer;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "audience_id", nullable = false)
    private Audience audience;
    @ManyToOne
    @JoinColumn(name = "timetable_id", nullable = false)
    private Timetable timetable;
    @ManyToMany(mappedBy = "classes")
    private Set<Group> groups;
}
