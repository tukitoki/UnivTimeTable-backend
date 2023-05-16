package ru.vsu.cs.timetable.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.vsu.cs.timetable.dto.faculty.CreateFacultyRequest;
import ru.vsu.cs.timetable.dto.faculty.FacultyDto;
import ru.vsu.cs.timetable.dto.faculty.FacultyPageDto;
import ru.vsu.cs.timetable.dto.page.SortDirection;

@Tag(name = "Faculty API", description = "API для работы с факультетами")
@SecurityRequirement(name = "bearer-key")
public interface FacultyApi {

    @Operation(
            summary = "Получение списка факультетов с фильтрацией и поиском"
    )
    FacultyPageDto getFacultiesByUniversity(
            @Parameter(description = "Номер страницы")
            int currentPage,
            @Parameter(description = "Количество элементов на странице")
            int pageSize,
            @Parameter(description = "Название факультета для поиска")
            String name,
            @Parameter(description = "Сортировка по алфавиту")
            SortDirection order,
            @Parameter(description = "Id университета, факультеты которого нужны")
            Long universityId
    );

    @Operation(
            summary = "Получение факультета по id"
    )
    FacultyDto getFacultyById(
            @Parameter(description = "Id факультета")
            Long id
    );

    @Operation(
            summary = "Создание факультета конкретного университета"
    )
    void createFaculty(
            @Parameter(description = "Параметры для создания факультета")
            CreateFacultyRequest createFacultyRequest,
            @Parameter(description = "Id университета, для которого создаются факультеты")
            Long id
    );

    @Operation(
            summary = "Удаление факультета конкретного университета"
    )
    void deleteFaculty(
            @Parameter(description = "Id факультета, который нужно удалить")
            Long id
    );

    @Operation(
            summary = "Обновление факультета конкретного университета"
    )
    void updateFaculty(
            @Parameter(description = "Измененная версия факульета")
            FacultyDto facultyDto,
            @Parameter(description = "Id факультета, который нужно обновить")
            Long id
    );
}
