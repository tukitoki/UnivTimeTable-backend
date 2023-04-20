package ru.vsu.cs.timetable.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;
import ru.vsu.cs.timetable.model.enums.DayOfWeekName;

@Getter
@Setter
@Table(name = "dayOfWeek")
@Entity
public class DayOfWeek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ColumnTransformer(read = "UPPER(name)", write = "LOWER(?)")
    @Enumerated(EnumType.STRING)
    private DayOfWeekName name;
    @NotNull
    private String displayName;
}
