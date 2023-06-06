package ru.vsu.cs.timetable.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vsu.cs.timetable.dto.university.UniversityDto;
import ru.vsu.cs.timetable.entity.Faculty;
import ru.vsu.cs.timetable.entity.University;
import ru.vsu.cs.timetable.entity.User;
import ru.vsu.cs.timetable.entity.enums.UserRole;
import ru.vsu.cs.timetable.mapper.UniversityMapper;
import ru.vsu.cs.timetable.repository.UniversityRepository;
import ru.vsu.cs.timetable.service.UniversityService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    private final UniversityDto universityVSUDto = new UniversityDto(1L, "ВГУ", "Воронеж");
    private University universityVSU = new University();
    private University universityVGTU = new University();

    @BeforeEach
    void setUp() {
        universityVSU.setId(1L);
        universityVSU.setCity("Воронеж");
        universityVSU.setName("ВГУ");

        universityVGTU.setId(2L);
        universityVGTU.setCity("Воронеж");
        universityVGTU.setName("ВГТУ");
    }

    @Test
    void getUniversityById() {
        when(universityRepository.findById(1L))
                .thenReturn(Optional.of(universityVSU));
        when(universityMapper.toDto(universityVSU)).
                thenReturn(universityVSUDto);

        UniversityDto universityDtoToCompare = universityServiceImpl.getUniversityById(1L);

        assertThat(universityDtoToCompare.getId()).isNotNull();
        assert(universityDtoToCompare.getId().equals(1L));
        assert(universityDtoToCompare.getUniversityName().equals("ВГУ"));
        assert(universityDtoToCompare.equals(universityVSUDto));
    }

    @Test
    void findUnivById() {
        when(universityRepository.findById(1L))
                .thenReturn(Optional.of(universityVSU));

        University universityToCompare = universityServiceImpl.findUnivById(1L);

        assertThat(universityToCompare.getName()).isNotNull();
        assert(universityToCompare.getId().equals(1L));
        assert(universityToCompare.getName().equals("ВГУ"));
        assert(universityToCompare.getCity().equals("Воронеж"));
        assert(universityToCompare.equals(universityVSU));
    }

    @Test
    void findUnivByName() {
        when(universityRepository.findByNameIgnoreCase("ВГУ"))
                .thenReturn(Optional.of(universityVSU));

        University universityToCompare = universityServiceImpl.findUnivByName("ВГУ");

        assertThat(universityToCompare.getName()).isNotNull();
        assert(universityToCompare.getName().equals("ВГУ"));
        assert(universityToCompare.equals(universityVSU));
    }

    @Test
    void findAllUniversities() {
        List<University> universityList = new ArrayList<>();
        universityList.add(universityVSU);
        universityList.add(universityVGTU);

        when(universityRepository.findAll())
                .thenReturn(universityList);

        List<University> universityListToCompare = universityServiceImpl.findAllUniversities();

        assertThat(universityListToCompare).isNotNull();
        assert(universityListToCompare.get(0).getName().equals("ВГУ"));
        assert(universityListToCompare.get(1).getName().equals("ВГТУ"));
        assert(universityListToCompare.equals(universityList));
    }

    @Test
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

        assert(universityToUpdate.getName().equals("МГУ"));
    }

    @Test
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