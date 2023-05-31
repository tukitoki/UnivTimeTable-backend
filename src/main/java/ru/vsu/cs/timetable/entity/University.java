package ru.vsu.cs.timetable.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "university")
@Entity
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @NotNull
    @Column(name = "city", nullable = false)
    private String city;
    @OneToMany(mappedBy = "university")
    private List<User> users;
    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL)
    private List<Faculty> faculties;
    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL)
    private List<Audience> audiences;

    @Override
    public String toString() {
        return "University{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
