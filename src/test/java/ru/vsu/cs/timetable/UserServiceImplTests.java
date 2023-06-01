package ru.vsu.cs.timetable;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.vsu.cs.timetable.controller.UserController;
import ru.vsu.cs.timetable.dto.user.UserDto;
import ru.vsu.cs.timetable.entity.User;
import ru.vsu.cs.timetable.service.impl.UserServiceImpl;
import ru.vsu.cs.timetable.entity.enums.UserRole;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

//@ContextConfiguration(classes = SwaggerConfig.class)
@AutoConfigureMockMvc
@SpringBootTest
public class UserServiceImplTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserController userController;

    @Test
    public void test() throws Exception{
        UserDto userDto = new UserDto(1213113421L, "Староста", "Иванов Иван Иванович", "ivan", "ivan@mail.ru",
                "password","Воронеж", 111L, 222L, 5L);
        assertThat(userDto.getEmail()).isNotNull();
    }

    @Test
    public void test2() throws Exception{
        UserDto userDto = new UserDto(1213113421L, "HEADMAN", "Иванов Иван Иванович", "ivan", "ivan@mail.ru",
                "password","Воронеж", null, null, null);
//        userServiceImpl.createUser(userDto);
        User user = userServiceImpl.getUserById(4L);
//        assertEquals(4L, user.getId());
        assertThat(userDto.getFacultyId()).isNull();
    }

}
