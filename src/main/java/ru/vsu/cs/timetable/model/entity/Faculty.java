package ru.vsu.cs.timetable.model.entity;

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
@Table(name = "faculty", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "university_id"})
})
@Entity
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false)
    private University university;
    @OneToMany(mappedBy = "faculty")
    private List<User> users;
    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
    private List<Group> groups;
    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
    private List<Audience> audiences;

    @PreRemove
    private void removeEntitiesFromFaculty() {
        for (User user : this.users) {
            user.setFaculty(null);
        }
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", university=" + university +
                '}';
    }
}
