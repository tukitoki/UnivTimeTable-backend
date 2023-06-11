package ru.vsu.cs.timetable.model.dto.page;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageModel<T> {

    @ArraySchema(schema = @Schema(description = "Список контента"))
    private List<T> contents;
    @Schema(description = "Текущая страница", example = "1")
    private int currentPage;
    @Schema(description = "Количество элементов на странице", example = "10")
    private int pageSize;
    @Schema(description = "Общее количество страниц", example = "10")
    private int totalPages;
    @Schema(description = "Общее количество элементов", example = "100")
    private long totalElements;

    public static <T> PageModel<T> of(List<T> contents, int currentPage, long totalElements, int pageSize,
                                      int totalPages) {
        return new PageModel<>(contents, currentPage, pageSize, totalPages, totalElements);
    }

}
