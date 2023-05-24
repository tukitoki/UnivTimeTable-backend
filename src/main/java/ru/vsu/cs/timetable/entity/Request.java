package ru.vsu.cs.timetable.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnTransformer;
import ru.vsu.cs.timetable.entity.enums.TypeClass;

import java.math.BigDecimal;
import java.util.List;
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
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    @Column(name = "subject_name", nullable = false)
    private String subjectName;
    @NotNull
    @Column(name = "subject_hour_per_week", nullable = false)
    private BigDecimal subjectHourPerWeek;
    @NotNull
    @ColumnTransformer(read = "UPPER(type_class)", write = "LOWER(?)")
    @Enumerated(EnumType.STRING)
    @Column(name = "type_class", nullable = false)
    private TypeClass typeClass;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "lecturer_id", nullable = false)
    private User lecturer;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;
    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    private List<ImpossibleTime> impossibleTimes;
    @ManyToMany
    @JoinTable(name = "request_equipment",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id"))
    private Set<Equipment> requiredEquipments;
}
