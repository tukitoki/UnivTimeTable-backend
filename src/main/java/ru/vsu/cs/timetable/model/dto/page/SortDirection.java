package ru.vsu.cs.timetable.model.dto.page;

import io.swagger.v3.oas.annotations.media.Schema;

public enum SortDirection {
    @Schema(description = "По возрастанию")
    ASC,
    @Schema(description = "По убыванию")
    DESC
    ;
}
