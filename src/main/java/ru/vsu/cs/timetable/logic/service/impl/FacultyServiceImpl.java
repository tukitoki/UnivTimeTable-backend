package ru.vsu.cs.timetable.logic.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.timetable.model.dto.faculty.CreateFacultyRequest;
import ru.vsu.cs.timetable.model.dto.faculty.FacultyDto;
import ru.vsu.cs.timetable.model.dto.faculty.FacultyPageDto;
import ru.vsu.cs.timetable.model.dto.page.PageModel;
import ru.vsu.cs.timetable.model.dto.page.SortDirection;
import ru.vsu.cs.timetable.exception.FacultyException;
import ru.vsu.cs.timetable.logic.service.FacultyService;
import ru.vsu.cs.timetable.logic.service.UniversityService;
import ru.vsu.cs.timetable.model.mapper.FacultyMapper;
import ru.vsu.cs.timetable.model.entity.Faculty;
import ru.vsu.cs.timetable.model.entity.University;
import ru.vsu.cs.timetable.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.List;

import static ru.vsu.cs.timetable.model.dto.page.SortDirection.ASC;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class FacultyServiceImpl implements FacultyService {

    private final UniversityService universityService;
    private final FacultyMapper facultyMapper;
    private final FacultyRepository facultyRepository;
    private final EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public FacultyPageDto getFacultiesByUniversity(int currentPage, int pageSize, String name,
                                                   SortDirection order, Long univId) {
        Page<Faculty> page = filerPage(currentPage, pageSize, name, order, univId);

        List<FacultyDto> facultyDtos = page.getContent()
                .stream()
                .map(facultyMapper::toDto)
                .toList();

        var pageModel = PageModel.of(facultyDtos, currentPage, page.getTotalElements(),
                pageSize, page.getTotalPages());

        return FacultyPageDto.builder()
                .facultiesPage(pageModel)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public FacultyDto getFacultyById(Long id) {
        Faculty faculty = findFacultyById(id);

        return facultyMapper.toDto(faculty);
    }

    @Override
    @Transactional(readOnly = true)
    public Faculty findFacultyById(Long id) {
        return facultyRepository.findById(id)
                .orElseThrow(FacultyException.CODE.ID_NOT_FOUND::get);
    }

    @Override
    public void createFaculty(CreateFacultyRequest createFacultyRequest, Long univId) {
        University university = universityService.findUnivById(univId);

        if (facultyRepository.findByNameAndUniversity(createFacultyRequest.getName(),
                university).isPresent()) {
            throw FacultyException.CODE.UNIV_FACULTY_ALREADY_PRESENT.get();
        }

        Faculty faculty = Faculty.builder()
                .name(createFacultyRequest.getName())
                .university(university)
                .build();

        faculty = facultyRepository.save(faculty);

        log.info("faculty: {} was successful saved", faculty);
    }

    @Override
    public void deleteFaculty(Long id) {
        Faculty faculty = findFacultyById(id);

        facultyRepository.delete(faculty);

        log.info("faculty: {} was successful deleted", faculty);
    }

    @Override
    public void updateFaculty(FacultyDto facultyDto, Long id) {
        Faculty oldFaculty = findFacultyById(id);
        Faculty newFaculty = facultyMapper.toEntity(facultyDto);

        var optionalFaculty = facultyRepository.findByNameAndUniversity(facultyDto.getName(),
                oldFaculty.getUniversity());
        if (optionalFaculty.isPresent() && !oldFaculty.getName().equals(newFaculty.getName())) {
            throw FacultyException.CODE.UNIV_FACULTY_ALREADY_PRESENT.get();
        }

        BeanUtils.copyProperties(newFaculty, oldFaculty, "id", "university", "groups", "audiences");
        oldFaculty = facultyRepository.save(oldFaculty);

        log.info("faculty {} was successful updated", oldFaculty);
    }

    private Page<Faculty> filerPage(int currentPage, int pageSize, String name,
                                    SortDirection order, Long univId) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Faculty> query = cb.createQuery(Faculty.class);

        Root<Faculty> root = query.from(Faculty.class);
        Join<Faculty, University> univ = root.join("university");
        query.select(root).distinct(true);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(univ.get("id"), univId));

        if (name != null) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        Path<Object> orderByName = root.get("name");
        Order alphabetOrder = order.equals(ASC)
                ? cb.asc(orderByName)
                : cb.desc(orderByName);

        List<Order> orderList = List.of(alphabetOrder, cb.asc(root.get("id")));
        query.orderBy(orderList);

        TypedQuery<Faculty> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<Faculty> faculties = typedQuery.getResultList();

        long count = countFilteredFaculties(name, univId);

        return new PageImpl<>(faculties, pageable, count);
    }


    private long countFilteredFaculties(String name, Long univId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);

        Root<Faculty> root = query.from(Faculty.class);
        Join<Faculty, University> univ = root.join("university");
        query.select(cb.countDistinct(root));

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(univ.get("id"), univId));

        if (name != null) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<Long> typedQuery = entityManager.createQuery(query);

        return typedQuery.getSingleResult();
    }
}
