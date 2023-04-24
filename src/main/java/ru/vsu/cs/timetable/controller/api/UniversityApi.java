package ru.vsu.cs.timetable.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.vsu.cs.timetable.dto.university.UniversityPageDto;
import ru.vsu.cs.timetable.dto.university.CreateUnivRequest;
import ru.vsu.cs.timetable.dto.university.UniversityDto;
import ru.vsu.cs.timetable.dto.page.SortDirection;

@Tag(name = "University API", description = "API для работы с университетами")
@SecurityRequirement(name = "JWT auth")
public interface UniversityApi {

    @Operation(
            summary = "Получение списка университетов с фильтрацией и поиском"
    )
    UniversityPageDto getAllUniversities(
            @Parameter(description = "Номер страницы")
            int pageNumber,
            @Parameter(description = "Количество элементов на странице")
            int pageSize,
            @Parameter(description = "Имя университета для поиска")
            String universityName,
            @Parameter(description = "Тип сортировки по алфавиту")
            SortDirection order
    );

    @Operation(
            summary = "Создание университета"
    )
    void createUniversity(
            @Parameter(description = "Параметры для создания университета")
            CreateUnivRequest createUnivRequest
    );

    @Operation(
            summary = "Редактирование университета"
    )
    void updateUniversity(
            @Parameter(description = "Измененная версия университета")
            UniversityDto universityDto,
            @Parameter(description = "Id университета для редактирования")
            Long id
    );

    @Operation(
            summary = "Удаление университета"
    )
    void deleteUniversity(
            @Parameter(description = "Id университета для удаления")
            Long id
    );
}
