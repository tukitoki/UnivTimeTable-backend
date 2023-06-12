package ru.vsu.cs.timetable.config.converter;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.vsu.cs.timetable.model.enums.UserRole;

@Component
public class UserRoleConverter implements Converter<String, UserRole> {

    @Override
    public UserRole convert(@NonNull String roleName) {
        return UserRole.fromName(roleName);
    }
}
