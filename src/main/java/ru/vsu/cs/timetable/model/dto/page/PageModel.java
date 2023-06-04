package ru.vsu.cs.timetable.model.dto.page;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageModel<T> {

    private List<T> contents;
    private int currentPage;
    private int pageSize;
    private int totalPages;
    private long totalElements;
    public static <T> PageModel<T> of(List<T> contents, int currentPage, long totalElements, int pageSize,
                                      int totalPages){
        return new PageModel<>(contents, currentPage, pageSize, totalPages, totalElements);
    }

}
