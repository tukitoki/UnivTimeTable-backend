package ru.vsu.cs.timetable.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vsu.cs.timetable.logic.service.UniversityService;
import ru.vsu.cs.timetable.logic.service.impl.FacultyServiceImpl;
import ru.vsu.cs.timetable.model.dto.faculty.CreateFacultyRequest;
import ru.vsu.cs.timetable.model.dto.faculty.FacultyDto;
import ru.vsu.cs.timetable.model.entity.Faculty;
import ru.vsu.cs.timetable.model.entity.University;
import ru.vsu.cs.timetable.model.mapper.FacultyMapper;
import ru.vsu.cs.timetable.repository.FacultyRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class FacultyServiceImplTest {

    @Mock
    private FacultyRepository facultyRepository;
    @Mock
    private FacultyMapper facultyMapper;
    @InjectMocks
    private FacultyServiceImpl facultyServiceImpl;
    @Mock
    private UniversityService universityService;

    private final FacultyDto facultyFKNDto = new FacultyDto(1L, "ФКН");
    private University universityVSU = new University();
    private University universityVGTU = new University();
    private Faculty facultyFKN = new Faculty();

    @BeforeEach
    void setUp() {
        universityVSU.setId(1L);
        universityVSU.setCity("Воронеж");
        universityVSU.setName("ВГУ");

        universityVGTU.setId(2L);
        universityVGTU.setCity("Воронеж");
        universityVGTU.setName("ВГTУ");

        facultyFKN.setId(1L);
        facultyFKN.setName("ФКН");
        facultyFKN.setUniversity(universityVSU);
    }

    @Test
    void getFacultyById() {
        when(facultyRepository.findById(1L))
                .thenReturn(Optional.of(facultyFKN));
        when(facultyMapper.toDto(facultyFKN)).
                thenReturn(facultyFKNDto);

        FacultyDto facultyDto = facultyServiceImpl.getFacultyById(1L);

        assertThat(facultyDto.getId()).isNotNull();
        assert (facultyDto.getId().equals(1L));
        assert (facultyDto.getName().equals("ФКН"));
        assert (facultyDto.equals(facultyFKNDto));
    }

    @Test
    void findFacultyById() {
        when(facultyRepository.findById(1L))
                .thenReturn(Optional.of(facultyFKN));

        Faculty facultyToCompare = facultyServiceImpl.findFacultyById(1L);

        assertThat(facultyToCompare.getName()).isNotNull();
        assert (facultyToCompare.getId().equals(1L));
        assert (facultyToCompare.getName().equals("ФКН"));
        assert (facultyToCompare.equals(facultyFKN));
    }

    @Test
    void createFaculty() {
        CreateFacultyRequest createFacultyRequest = new CreateFacultyRequest();
        createFacultyRequest.setName("РГФ");
        //т.к. id - Serial, то сервисом всегда будет создаваться факультет с id=null

        when(universityService.findUnivById(1L)).
                thenReturn(universityVSU);

        Faculty facultyToCreate = new Faculty();
        facultyToCreate.setName("РГФ");
        facultyToCreate.setUniversity(universityVSU);

        when(facultyRepository.save(any())).thenReturn(facultyToCreate);
        facultyServiceImpl.createFaculty(createFacultyRequest, 1L);
    }

    @Test
    void deleteFaculty() {
        Faculty facultyToDelete = new Faculty();
        facultyToDelete.setId(4L);
        facultyToDelete.setName("ЖурФак");
        facultyToDelete.setUniversity(universityVSU);

        when(facultyRepository.findById(4L))
                .thenReturn(Optional.of(facultyToDelete));
        facultyServiceImpl.deleteFaculty(4L);
    }

    @Test
    void updateFaculty() {
        Faculty facultyToUpdate = new Faculty();
        facultyToUpdate.setId(5L);
        facultyToUpdate.setName("Геологический факультет");
        facultyToUpdate.setUniversity(universityVSU);

        FacultyDto newFacultyDto = new FacultyDto(5L, "Математический факультет");

        Faculty newFaculty = new Faculty();
        newFaculty.setId(5L);
        newFaculty.setName("Математический факультет");
        newFaculty.setUniversity(universityVGTU);

        when(facultyRepository.findById(5L))
                .thenReturn(Optional.of(facultyToUpdate));
        when(facultyMapper.toEntity(newFacultyDto)).
                thenReturn(newFaculty);
        when(facultyRepository.findByNameAndUniversity("Математический факультет", universityVSU)).
                thenReturn(Optional.empty());

        when(facultyRepository.save(any())).
                thenReturn(facultyToUpdate);

        facultyServiceImpl.updateFaculty(newFacultyDto, 5L);

        assert (facultyToUpdate.getName().equals("Математический факультет"));
    }
}