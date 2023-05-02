package ru.vsu.cs.timetable.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;
import ru.vsu.cs.timetable.model.enums.EquipmentName;

import java.util.Set;

@Getter
@Setter
@Table(name = "equipment")
@Entity
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    @ColumnTransformer(read = "UPPER(name)", write = "LOWER(?)")
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private EquipmentName name;
    @NotNull
    @Column(name = "display_name", nullable = false)
    private String displayName;
    @ManyToMany(mappedBy = "equipments", fetch = FetchType.LAZY)
    private Set<Audience> audiences;
    @ManyToMany(mappedBy = "requiredEquipments", fetch = FetchType.LAZY)
    private Set<Request> requests;
}
