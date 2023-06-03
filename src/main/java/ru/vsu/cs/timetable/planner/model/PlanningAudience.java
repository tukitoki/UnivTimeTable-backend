package ru.vsu.cs.timetable.planner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.entity.Equipment;

import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PlanningAudience {

    private Long id;
    private Integer audienceNumber;
    private Long capacity;
    private Set<Equipment> equipments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanningAudience that = (PlanningAudience) o;
        return Objects.equals(id, that.id) && Objects.equals(audienceNumber, that.audienceNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, audienceNumber);
    }
}
