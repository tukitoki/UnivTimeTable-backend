package ru.vsu.cs.timetable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.timetable.controller.api.FacultyApi;
import ru.vsu.cs.timetable.logic.service.FacultyService;
import ru.vsu.cs.timetable.model.dto.faculty.CreateFacultyRequest;
import ru.vsu.cs.timetable.model.dto.faculty.FacultyDto;
import ru.vsu.cs.timetable.model.dto.faculty.FacultyPageDto;
import ru.vsu.cs.timetable.model.dto.page.SortDirection;

import java.util.List;

@RequiredArgsConstructor
@PreAuthorize("hasAuthority('CREATE_FACULTY_AUTHORITY')")
@RequestMapping("/university")
@RestController
public class FacultyController implements FacultyApi {

    private final FacultyService facultyService;

    @Override
    @GetMapping("/{univId}/faculties")
    public ResponseEntity<FacultyPageDto> getFacultiesByUniversity(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "ASC") SortDirection order,
            @PathVariable Long univId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(facultyService.getFacultiesByUniversity(currentPage, pageSize, name, order, univId));
    }

    @Override
    @GetMapping("/{univId}/faculties/all")
    public ResponseEntity<List<FacultyDto>> getFacultiesByUniversity(@PathVariable Long univId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(facultyService.getFacultiesByUniversity(univId));
    }

    @Override
    @GetMapping("/faculty/{id}")
    public ResponseEntity<FacultyDto> getFacultyById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(facultyService.getFacultyById(id));
    }

    @Override
    @PostMapping("/{univId}/faculty/create")
    public ResponseEntity<Void> createFaculty(@RequestBody CreateFacultyRequest createFacultyRequest,
                                              @PathVariable Long univId) {
        facultyService.createFaculty(createFacultyRequest, univId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Override
    @DeleteMapping("/faculty/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    @PutMapping("/faculty/{id}")
    public ResponseEntity<Void> updateFaculty(@RequestBody FacultyDto facultyDto,
                                              @PathVariable Long id) {
        facultyService.updateFaculty(facultyDto, id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
