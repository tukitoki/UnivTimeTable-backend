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
import ru.vsu.cs.timetable.logic.service.FacultyService;
import ru.vsu.cs.timetable.logic.service.UniversityService;
import ru.vsu.cs.timetable.logic.service.impl.AudienceServiceImpl;
import ru.vsu.cs.timetable.model.dto.audience.CreateAudienceRequest;
import ru.vsu.cs.timetable.model.dto.week_time.DayTimes;
import ru.vsu.cs.timetable.model.entity.Class;
import ru.vsu.cs.timetable.model.entity.*;
import ru.vsu.cs.timetable.model.mapper.AudienceMapper;
import ru.vsu.cs.timetable.repository.AudienceRepository;
import ru.vsu.cs.timetable.repository.EquipmentRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class AudienceServiceImplTest {

    @Mock
    private AudienceRepository audienceRepository;
    @Mock
    private EquipmentRepository equipmentRepository;
    @InjectMocks
    private AudienceServiceImpl audienceServiceImpl;
    @Mock
    private UniversityService universityService;
    @Mock
    private FacultyService facultyService;
    @Mock
    private AudienceMapper audienceMapper;
    private List<Audience> audienceList;
    private Audience audience;
    private Faculty facultyFKN;
    private University universityVSU;

    @BeforeEach
    void setUp() {
        audienceList = new ArrayList<>();
        audience = new Audience();
        facultyFKN = new Faculty();
        universityVSU = new University();

        audience.setAudienceNumber(123);
        audience.setCapacity(50L);
        audience.setId(1L);
        audience.setFaculty(facultyFKN);

        audienceList.add(audience);

        facultyFKN.setId(1L);
        facultyFKN.setName("ФКН");
        facultyFKN.setAudiences(audienceList);
    }

    @Test
    @DisplayName("Should successfully create audience")
    void createAudience() {
        List<String> equipmentToAdd = new ArrayList<>();
        equipmentToAdd.add("проектор");
        CreateAudienceRequest createAudienceRequest = new CreateAudienceRequest(385, 30L, equipmentToAdd);

        when(universityService.findUnivById(1L)).
                thenReturn(universityVSU);
        when(facultyService.findFacultyById(1L)).
                thenReturn(facultyFKN);

        Equipment equipmentName = new Equipment();
        equipmentName.setDisplayName("проектор");
        equipmentName.setId(1L);
        equipmentName.setName("проектор");

        Set<Equipment> equipment = new LinkedHashSet<>();
        equipment.add(equipmentName);

        Audience audienceToCreate = new Audience();
        audienceToCreate.setAudienceNumber(385);
        audienceToCreate.setCapacity(30L);
        audienceToCreate.setId(1L);
        audienceToCreate.setUniversity(universityVSU);
        audienceToCreate.setFaculty(facultyFKN);
        audienceToCreate.setEquipments(equipment);

        when(audienceMapper.toEntity(createAudienceRequest, universityVSU, facultyFKN, equipment)).
                thenReturn(audienceToCreate);
        when(audienceRepository.findByAudienceNumberAndFaculty(385, facultyFKN)).
                thenReturn(Optional.of(audienceToCreate));
        when(equipmentRepository.findByDisplayNameIgnoreCase(equipmentName.getDisplayName())).
                thenReturn(Optional.of(equipmentName));


        audienceServiceImpl.createAudience(createAudienceRequest, 1L, 1L);
        Audience createdAudience = audienceServiceImpl.findAudienceByNumberAndFaculty(385, facultyFKN);

        assertEquals(createdAudience.getAudienceNumber(), 385);
        assertEquals(createdAudience.getId(), 1L);
        assertEquals(createdAudience.getCapacity(), 30L);
        assertEquals(createdAudience.getFaculty().getName(), "ФКН");
    }

    @Test
    @DisplayName("Should successfully find audience by number and faculty")
    void findAudienceByNumberAndFaculty() {
        when(audienceRepository.findByAudienceNumberAndFaculty(123, audience.getFaculty()))
                .thenReturn(Optional.of(audience));

        Audience audienceToCompare = audienceServiceImpl.findAudienceByNumberAndFaculty(123, facultyFKN);

        assertEquals(audienceToCompare.getId(), 1L);
        assertEquals(audienceToCompare.getCapacity(), 50L);
        assertEquals(audienceToCompare.getFaculty().getName(), "ФКН");
    }

    @Test
    @DisplayName("Should successfully find free audience by faculty")
    void getFreeAudienceByFaculty() {
        Map<Audience, List<DayTimes>> freeAudiences;
        List<Class> classList = new ArrayList<>();
        classList.add(new Class());
        audience.setClasses(classList);

        when(audienceRepository.findAllByFaculty(facultyFKN)).thenReturn(audienceList);

        freeAudiences = audienceServiceImpl.getFreeAudienceByFaculty(facultyFKN);

        assertEquals(freeAudiences.size(), 1);
        assertEquals(freeAudiences.get(audience).size(), 7);
    }
}