package ru.vsu.cs.timetable.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;
import ru.vsu.cs.timetable.model.enums.DayOfWeekEnum;

import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "impossible_time")
@Entity
public class ImpossibleTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    @ColumnTransformer(read = "UPPER(dayOfWeek)", write = "LOWER(?)")
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeekEnum dayOfWeek;
    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    private Request request;
    @NotNull
    @Column(name = "time_from", nullable = false)
    private Time timeFrom;
}
