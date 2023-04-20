package ru.vsu.cs.timetable.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "impossible_time")
public class ImpossibleTime {

    @NotNull
    @EmbeddedId
    private ImpossibleTimeId id;
    @NotNull
    private Time timeFrom;
}
