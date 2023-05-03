package ru.vsu.cs.timetable.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.timetable.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(@NotNull String username);

    Optional<User> findByEmail(@NotNull String email);

    @Query(value = """
            SELECT u.city
            FROM User u
            """)
    List<String> findAllUserCities();
}
