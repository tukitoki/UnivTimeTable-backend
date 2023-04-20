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
    private Long id;
    @NotNull
    @ColumnTransformer(read = "UPPER(name)", write = "LOWER(?)")
    @Enumerated(EnumType.STRING)
    private EquipmentName name;
    @NotNull
    private String displayName;
    @ManyToMany(mappedBy = "equipments", fetch = FetchType.LAZY)
    private Set<Class> classes;
    @ManyToMany(mappedBy = "requiredEquipments", fetch = FetchType.LAZY)
    private Set<Request> requests;
}
