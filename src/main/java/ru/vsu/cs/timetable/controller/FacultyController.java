package ru.vsu.cs.timetable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.timetable.controller.api.FacultyApi;
import ru.vsu.cs.timetable.dto.faculty.CreateFacultyRequest;
import ru.vsu.cs.timetable.dto.faculty.FacultyDto;
import ru.vsu.cs.timetable.dto.faculty.FacultyPageDto;
import ru.vsu.cs.timetable.dto.page.SortDirection;
import ru.vsu.cs.timetable.service.FacultyService;

@RequiredArgsConstructor
@PreAuthorize("hasAuthority('CREATE_FACULTY_AUTHORITY')")
@RequestMapping("/university")
@RestController
public class FacultyController implements FacultyApi {

    private final FacultyService facultyService;

    @Override
    @GetMapping("/{univId}/faculties")
    public FacultyPageDto getFacultiesByUniversity(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "ASC") SortDirection order,
            @PathVariable Long univId
    ) {
        return facultyService.getFacultiesByUniversity(currentPage, pageSize, name, order, univId);
    }

    @Override
    @GetMapping("/faculty/{id}")
    public FacultyDto getFacultyById(@PathVariable Long id) {
        return facultyService.getFacultyById(id);
    }

    @Override
    @PostMapping("/{univId}/faculty/create")
    public void createFaculty(@RequestBody CreateFacultyRequest createFacultyRequest,
                              @PathVariable Long univId) {
        facultyService.createFaculty(createFacultyRequest, univId);
    }

    @Override
    @DeleteMapping("/faculty/{id}")
    public void deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
    }

    @Override
    @PutMapping("/faculty/{id}")
    public void updateFaculty(@RequestBody FacultyDto facultyDto,
                              @PathVariable Long id) {
        facultyService.updateFaculty(facultyDto, id);
    }
}
