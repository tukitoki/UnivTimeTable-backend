package ru.vsu.cs.timetable.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "audience")
@Entity
public class Audience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Integer audienceNumber;
    @NotNull
    private Long capacity;
    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;
    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;
    @ManyToMany
    @JoinTable(name = "audience_equipment",
            joinColumns = @JoinColumn(name = "audience_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id"))
    private Set<Equipment> equipments;
}
