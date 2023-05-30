package ru.vsu.cs.timetable.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static ru.vsu.cs.timetable.entity.enums.Authority.*;

@Getter
@RequiredArgsConstructor
public enum UserRole {

    HEADMAN(Set.of(GET_SCHEDULE)),
    LECTURER(Set.of(SEND_REQUEST_AUTHORITY, MOVE_CLASS_AUTHORITY, MAKE_TIMETABLE_AUTHORITY, GET_SCHEDULE)),
    ADMIN(Set.of(CREATE_USER_AUTHORITY, CREATE_UNIVERSITY_AUTHORITY,
            CREATE_FACULTY_AUTHORITY, CREATE_AUDIENCE_AUTHORITY, CREATE_GROUP_AUTHORITY))
    ;

    private final Set<Authority> authorities;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.name()))
                .collect(Collectors.toSet());
    }
}
