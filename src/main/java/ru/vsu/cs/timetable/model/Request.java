package ru.vsu.cs.timetable.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vsu.cs.timetable.model.enums.TypeClass;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "request")
@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String subjectName;
    @NotNull
    private Long lecturerId;
    @NotNull
    private Integer subjectHourPerWeek;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TypeClass typeClass;
    @NotNull
    private Long groupId;
    @ManyToMany
    @JoinTable(name = "request_equipment",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id"))
    private Set<Equipment> requiredEquipments;
}
