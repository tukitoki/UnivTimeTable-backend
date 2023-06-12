package ru.vsu.cs.timetable.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnTransformer;
import ru.vsu.cs.timetable.model.enums.DayOfWeekEnum;
import ru.vsu.cs.timetable.model.enums.TypeClass;
import ru.vsu.cs.timetable.model.enums.UserRole;
import ru.vsu.cs.timetable.model.enums.WeekType;

import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "group_class",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> groups;

    @PreRemove
    public void removeClassFromGroup() {
        this.groups.forEach(group -> {
            group.getClasses().remove(this);
        });
        this.groups = null;
    }

    @Override
    public String toString() {
        return subjectName +
                ", " + typeClass +
                ", " + lecturer +
                ", " + audience;
    }

    public String toExcelFormat(UserRole userRole) {
        StringBuilder excelFormatString = new StringBuilder(subjectName +
                ", " + lecturer.getFullName() +
                ", " + typeClass +
                ", Аудитория: " + audience.getAudienceNumber());
        if (userRole == UserRole.LECTURER) {
            String groups = ", " + getGroups().stream()
                    .collect(
                            Collectors.groupingBy(
                                    Group::getCourseNumber,
                                    Collectors.mapping(
                                            group -> group.getGroupNumber().toString(),
                                            Collectors.joining(", ")
                                    )
                            )
                    )
                    .entrySet().stream()
                    .map(entry -> "Курс: " + entry.getKey() + ", Группы: " + entry.getValue())
                    .collect(Collectors.joining("; "));
            excelFormatString.append(groups);
        }
        return excelFormatString.toString();
    }

    public String toStringMoveClass() {
        return "Предмет: " + subjectName +
                ", Аудитория: " + audience.getAudienceNumber() +
                ", День недели: " + dayOfWeek +
                ", Тип недели: " + weekType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Class aClass = (Class) o;
        return Objects.equals(id, aClass.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
