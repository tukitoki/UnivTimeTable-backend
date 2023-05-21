package ru.vsu.cs.timetable.repository;

import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.timetable.entity.Equipment;

import java.util.List;
import java.util.Optional;

public interface EquipmentRepository extends CrudRepository<Equipment, Long> {

    Optional<Equipment> findByDisplayNameIgnoreCase(@NotNull String displayName);

    @NonNull
    List<Equipment> findAll();
}
