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
import ru.vsu.cs.timetable.dto.audience.CreateAudienceRequest;
import ru.vsu.cs.timetable.entity.Audience;
import ru.vsu.cs.timetable.entity.Equipment;
import ru.vsu.cs.timetable.entity.Faculty;
import ru.vsu.cs.timetable.entity.University;
import ru.vsu.cs.timetable.mapper.AudienceMapper;
import ru.vsu.cs.timetable.repository.AudienceRepository;
import ru.vsu.cs.timetable.repository.EquipmentRepository;
import ru.vsu.cs.timetable.service.FacultyService;
import ru.vsu.cs.timetable.service.UniversityService;

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
    private AudienceServiceImpl audienceService;
    @Mock
    private UniversityService universityService;
    @Mock
    private FacultyService facultyService;
    @Mock
    private AudienceMapper audienceMapper;

    private Audience audience;
    private Faculty facultyFKN;
    private University universityVSU;

    @BeforeEach
    void setUp() {
        audience = new Audience();
        facultyFKN = new Faculty();
        universityVSU = new University();

        audience.setAudienceNumber(123);
        audience.setCapacity(50L);
        audience.setId(1L);
        audience.setFaculty(facultyFKN);
        facultyFKN.setId(1L);
        facultyFKN.setName("ФКН");
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
        when(audienceRepository.findByAudienceNumberAndFaculty(385, facultyFKN)).thenReturn(Optional.of(audienceToCreate));
        when(equipmentRepository.findByDisplayNameIgnoreCase(equipmentName.getDisplayName())).
                thenReturn(Optional.of(equipmentName));


        audienceService.createAudience(createAudienceRequest, 1L, 1L);
        Audience createdAudience = audienceService.findAudienceByNumberAndFaculty(385, facultyFKN);

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

        Audience audienceToCompare = audienceService.findAudienceByNumberAndFaculty(123, facultyFKN);

        assertEquals(audienceToCompare.getId(), 1L);
        assertEquals(audienceToCompare.getCapacity(), 50L);
        assertEquals(audienceToCompare.getFaculty().getName(), "ФКН");
    }

    @Test
    void getFreeAudienceByFaculty() {
    }
}