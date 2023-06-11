package ru.vsu.cs.timetable.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vsu.cs.timetable.logic.service.impl.UniversityServiceImpl;
import ru.vsu.cs.timetable.model.dto.university.UniversityDto;
import ru.vsu.cs.timetable.model.entity.University;
import ru.vsu.cs.timetable.model.entity.User;
import ru.vsu.cs.timetable.model.mapper.UniversityMapper;
import ru.vsu.cs.timetable.repository.UniversityRepository;
import ru.vsu.cs.timetable.logic.service.UniversityService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class UniversityServiceImplTest {

    @Mock
    private UniversityRepository universityRepository;
    @Mock
    private UniversityMapper universityMapper;
    @InjectMocks
    private UniversityServiceImpl universityServiceImpl;
    @Mock
    private UniversityService universityService;
    private UniversityDto universityVSUDto;
    private University universityVSU;
    private University universityVGTU;

    @BeforeEach
    void setUp() {
        universityVSUDto = new UniversityDto(1L, "ВГУ", "Воронеж");
        universityVSU = new University();
        universityVGTU = new University();

        universityVSU.setId(1L);
        universityVSU.setCity("Воронеж");
        universityVSU.setName("ВГУ");

        universityVGTU.setId(2L);
        universityVGTU.setCity("Воронеж");
        universityVGTU.setName("ВГТУ");
    }

    @Test
    @DisplayName("Should successfully find university DTO by id")
    void getUniversityById() {
        when(universityRepository.findById(1L))
                .thenReturn(Optional.of(universityVSU));
        when(universityMapper.toDto(universityVSU)).
                thenReturn(universityVSUDto);

        UniversityDto universityDtoToCompare = universityServiceImpl.getUniversityById(1L);

        assertThat(universityDtoToCompare.getId()).isNotNull();
        assertEquals(universityDtoToCompare.getId(), 1L);
        assertEquals(universityDtoToCompare.getUniversityName(), "ВГУ");
        assertEquals(universityDtoToCompare, universityVSUDto);
    }

    @Test
    @DisplayName("Should successfully find university by id")
    void findUnivById() {
        when(universityRepository.findById(1L))
                .thenReturn(Optional.of(universityVSU));

        University universityToCompare = universityServiceImpl.findUnivById(1L);

        assertThat(universityToCompare.getName()).isNotNull();
        assertEquals(universityToCompare.getId(), 1L);
        assertEquals(universityToCompare.getName(), "ВГУ");
        assertEquals(universityToCompare.getCity(), "Воронеж");
        assertEquals(universityToCompare, universityVSU);
    }

    @Test
    @DisplayName("Should successfully find university by name")
    void findUnivByName() {
        when(universityRepository.findByNameIgnoreCase("ВГУ"))
                .thenReturn(Optional.of(universityVSU));

        University universityToCompare = universityServiceImpl.findUnivByName("ВГУ");

        assertThat(universityToCompare.getName()).isNotNull();
        assertEquals(universityToCompare.getName(), ("ВГУ"));
        assertEquals(universityToCompare, universityVSU);
    }

    @Test
    @DisplayName("Should successfully find all universities")
    void findAllUniversities() {
        List<University> universityList = new ArrayList<>();
        universityList.add(universityVSU);
        universityList.add(universityVGTU);

        when(universityRepository.findAll())
                .thenReturn(universityList);

        List<University> universityListToCompare = universityServiceImpl.findAllUniversities();

        assertThat(universityListToCompare).isNotNull();
        assertEquals(universityListToCompare.get(0).getName(), "ВГУ");
        assertEquals(universityListToCompare.get(1).getName(), "ВГТУ");
        assertEquals(universityListToCompare, universityList);
    }

    @Test
    @DisplayName("Should successfully create university")
    void createUniversity() {
        UniversityDto universityToCreateDto = new UniversityDto(5L, "Бауманка", "Москва");
        University universityBAYMANKA = new University();
        universityBAYMANKA.setId(5L);
        universityBAYMANKA.setCity("Бауманка");
        universityBAYMANKA.setName("Москва");

        when(universityRepository.findByName("Бауманка")).
                thenReturn(Optional.empty());
        when(universityRepository.save(any())).
                thenReturn(universityBAYMANKA);

        universityServiceImpl.createUniversity(universityToCreateDto);
    }

    @Test
    @DisplayName("Should successfully update university")
    void updateUniversity() {
        University universityToUpdate = new University();
        universityToUpdate.setId(4L);
        universityToUpdate.setName("Плехановский университет");
        universityToUpdate.setCity("Москва");

        UniversityDto newUniversityDto = new UniversityDto(4L, "МГУ", "Москва");

        University newUniversity = new University();
        newUniversity.setId(4L);
        newUniversity.setName("МГУ");
        newUniversity.setCity("Москва");

        when(universityRepository.findById(4L))
                .thenReturn(Optional.of(universityToUpdate));
        when(universityMapper.toEntity(newUniversityDto)).
                thenReturn(newUniversity);
        when(universityRepository.findByName("МГУ")).
                thenReturn(Optional.empty());

        when(universityRepository.save(any())).
                thenReturn(universityToUpdate);

        universityServiceImpl.updateUniversity(newUniversityDto, 4L);

        assertEquals(universityToUpdate.getName(), "МГУ");
    }

    @Test
    @DisplayName("Should successfully delete university")
    void deleteUniversity() {
        List<User> users = new ArrayList<>();

        University universityToDelete = new University();
        universityToDelete.setId(3L);
        universityToDelete.setName("Политех");
        universityToDelete.setCity("Воронеж");
        universityToDelete.setUsers(users);

        when(universityRepository.findById(3L))
                .thenReturn(Optional.of(universityToDelete));
        universityServiceImpl.deleteUniversity(3L);
    }
}