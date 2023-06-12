package ru.vsu.cs.timetable.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnTransformer;
import ru.vsu.cs.timetable.model.enums.DayOfWeekEnum;

import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
@SuperBuilder
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
    @ColumnTransformer(read = "UPPER(day_of_week)", write = "LOWER(?)")
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeekEnum dayOfWeek;
    @NotNull
    @Column(name = "time_from", nullable = false)
    private LocalTime timeFrom;
    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    private Request request;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImpossibleTime that = (ImpossibleTime) o;
        return dayOfWeek == that.dayOfWeek && Objects.equals(timeFrom, that.timeFrom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayOfWeek, timeFrom);
    }

    @Override
    public String toString() {
        return "ImpossibleTime{" +
                "id=" + id +
                ", dayOfWeek=" + dayOfWeek +
                ", timeFrom=" + timeFrom +
                '}';
    }
}
