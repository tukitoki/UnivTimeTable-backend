package ru.vsu.cs.timetable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.timetable.controller.api.UniversityApi;
import ru.vsu.cs.timetable.model.dto.page.SortDirection;
import ru.vsu.cs.timetable.model.dto.university.UniversityDto;
import ru.vsu.cs.timetable.model.dto.university.UniversityPageDto;
import ru.vsu.cs.timetable.logic.service.UniversityService;

@RequiredArgsConstructor
@PreAuthorize("hasAuthority('CREATE_UNIVERSITY_AUTHORITY')")
@RequestMapping("/universities")
@RestController
public class UniversityController implements UniversityApi {

    private final UniversityService universityService;

    @Override
    @GetMapping()
    public ResponseEntity<UniversityPageDto> getAllUniversities(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String universityName,
            @RequestParam(defaultValue = "ASC") SortDirection order
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(universityService.getAllUniversities(currentPage, pageSize, universityName, order));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UniversityDto> getUniversityById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(universityService.getUniversityById(id));
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<Void> createUniversity(@RequestBody UniversityDto createUnivRequest) {
        universityService.createUniversity(createUnivRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUniversity(@RequestBody UniversityDto universityDto,
                                                 @PathVariable Long id) {
        universityService.updateUniversity(universityDto, id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUniversity(@PathVariable Long id) {
        universityService.deleteUniversity(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
