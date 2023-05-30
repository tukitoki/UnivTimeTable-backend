package ru.vsu.cs.timetable.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "audience", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"audience_number", "faculty_id", "university_id"})
})
@Entity
public class Audience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    @Column(name = "audience_number", nullable = false)
    private Integer audienceNumber;
    @NotNull
    @Column(name = "capacity", nullable = false)
    private Long capacity;
    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;
    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false)
    private University university;
    @OneToMany(mappedBy = "audience", cascade = CascadeType.ALL)
    private List<Class> classes;
    @ManyToMany
    @JoinTable(name = "audience_equipment",
            joinColumns = @JoinColumn(name = "audience_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id"))
    private Set<Equipment> equipments;
}
