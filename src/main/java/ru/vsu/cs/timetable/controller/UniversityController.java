package ru.vsu.cs.timetable.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.timetable.controller.api.UniversityApi;
import ru.vsu.cs.timetable.dto.page.SortDirection;
import ru.vsu.cs.timetable.dto.university.CreateUnivRequest;
import ru.vsu.cs.timetable.dto.university.UniversityDto;
import ru.vsu.cs.timetable.dto.university.UniversityPageDto;

@RequiredArgsConstructor
@RequestMapping
@RestController
public class UniversityController implements UniversityApi {

    @Override
    @GetMapping("/universities")
    public UniversityPageDto getAllUniversities(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String universityName,
            @RequestParam(defaultValue = "ASC") SortDirection order) {
        return null;
    }

    @Override
    @PostMapping("/university/create")
    public void createUniversity(@RequestBody CreateUnivRequest createUnivRequest) {

    }

    @Override
    @PutMapping("/university/{id}")
    public void updateUniversity(@RequestBody UniversityDto universityDto,
                                 @PathVariable Long id) {

    }

    @Override
    @DeleteMapping("/university/{id}")
    public void deleteUniversity(@PathVariable Long id) {

    }
}
