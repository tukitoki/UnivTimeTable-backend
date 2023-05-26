package ru.vsu.cs.timetable.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnTransformer;
import ru.vsu.cs.timetable.entity.enums.DayOfWeekEnum;
import ru.vsu.cs.timetable.entity.enums.TypeClass;
import ru.vsu.cs.timetable.entity.enums.WeekType;

import java.time.LocalTime;
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
    private LocalTime startTime;
    @NotNull
    @ColumnTransformer(read = "UPPER(type_class)", write = "LOWER(?)")
    @Enumerated(EnumType.STRING)
    @Column(name = "type_class", nullable = false)
    private TypeClass typeClass;
    @NotNull
    @ColumnTransformer(read = "UPPER(day_of_week)", write = "LOWER(?)")
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeekEnum dayOfWeek;
    @NotNull
    @ColumnTransformer(read = "UPPER(week_type)", write = "LOWER(?)")
    @Enumerated(EnumType.STRING)
    @Column(name = "week_type", nullable = false)
    private WeekType weekType;
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
    @ManyToMany
    @JoinTable(name = "group_class",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> groups;

    @Override
    public String toString() {
        return subjectName +
                ", " + typeClass +
                ", " + lecturer.getFullName() +
                ", " + audience.getAudienceNumber();
    }
}
