package ru.vsu.cs.timetable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.timetable.controller.api.FacultyApi;
import ru.vsu.cs.timetable.dto.faculty.CreateFacultyRequest;
import ru.vsu.cs.timetable.dto.faculty.FacultyDto;
import ru.vsu.cs.timetable.dto.faculty.FacultyPageDto;
import ru.vsu.cs.timetable.dto.page.SortDirection;

@RequiredArgsConstructor
@RequestMapping("/university/{univId}")
@RestController
public class FacultyController implements FacultyApi {

    @Override
    @GetMapping("/faculties")
    public FacultyPageDto getFacultiesByUniversity(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "ASC") SortDirection order,
            @PathVariable Long univId
    ) {
        return null;
    }

    @Override
    @PostMapping("/faculty/create")
    public void createFaculty(@RequestBody CreateFacultyRequest createFacultyRequest,
                              @PathVariable Long univId) {

    }

    @Override
    @DeleteMapping("/faculty/{id}")
    public void deleteFaculty(@PathVariable Long id) {

    }

    @Override
    @PutMapping("/faculty/{id}")
    public void updateFaculty(@RequestBody FacultyDto facultyDto,
                              @PathVariable Long id) {

    }
}
