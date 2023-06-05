package ru.vsu.cs.timetable.repository;

import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.timetable.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(@NotNull String username);

    Optional<User> findByEmail(@NotNull String email);

    @Query(value = """
            SELECT DISTINCT u.city
            FROM User u
            """)
    List<String> findAllUserCities();

    @NonNull
    List<User> findAll();

    @Query(value = """
            SELECT u
            FROM User u
            WHERE u.role = "HEADMAN" and u.group = null
            and u.faculty.id = :facultyId
            """)
    List<User> findAllFreeHeadmenByFaculty(@NotNull Long facultyId);
}
