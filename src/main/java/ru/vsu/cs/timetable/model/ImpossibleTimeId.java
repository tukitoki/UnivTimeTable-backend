package ru.vsu.cs.timetable.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ImpossibleTimeId implements Serializable {

    @NotNull
    private Long requestId;
    @NotNull
    private Long dayOfWeekId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImpossibleTimeId that = (ImpossibleTimeId) o;
        return Objects.equals(requestId, that.requestId) && Objects.equals(dayOfWeekId, that.dayOfWeekId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId, dayOfWeekId);
    }
}
