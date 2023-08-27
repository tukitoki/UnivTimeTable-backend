package ru.vsu.cs.timetable.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static ru.vsu.cs.timetable.model.enums.Authority.*;

@Getter
@RequiredArgsConstructor
public enum UserRole {

    HEADMAN(Set.of(GET_SCHEDULE), "Староста"),
    LECTURER(Set.of(SEND_REQUEST_AUTHORITY, MOVE_CLASS_AUTHORITY, GET_SCHEDULE),
            "Преподаватель"),
    LECTURER_SCHEDULER(Set.of(SEND_REQUEST_AUTHORITY, MOVE_CLASS_AUTHORITY, GET_SCHEDULE, MAKE_TIMETABLE_AUTHORITY),
            "Преподаватель составитель"),
    ADMIN(Set.of(CREATE_USER_AUTHORITY, CREATE_UNIVERSITY_AUTHORITY,
            CREATE_FACULTY_AUTHORITY, CREATE_AUDIENCE_AUTHORITY, CREATE_GROUP_AUTHORITY),
            "Администратор");

    private final Set<Authority> authorities;
    private final String name;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.name()))
                .collect(Collectors.toSet());
    }

    public static UserRole fromName(String roleName) {
        for (UserRole typeClass : UserRole.values()) {
            if (typeClass.name.equalsIgnoreCase(roleName)) {
                return typeClass;
            }
        }
        return null;
    }

    @Override
    @JsonValue
    public String toString() {
        return this.name;
    }
}
