package ru.vsu.cs.timetable.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    private Audience audience = new Audience();
    private Faculty facultyFKN = new Faculty();
    private University universityVSU = new University();

    @BeforeEach
    void setUp() {
        audience.setAudienceNumber(123);
        audience.setCapacity(50L);
        audience.setId(1L);
        audience.setFaculty(facultyFKN);
        facultyFKN.setId(1L);
        facultyFKN.setName("ФКН");
    }

    @Test
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

        assert(createdAudience.getAudienceNumber().equals(385));
        assert(createdAudience.getId().equals(1L));
        assert(createdAudience.getCapacity().equals(30L));
        assert(createdAudience.getFaculty().getName().equals("ФКН"));
    }

    @Test
    void findAudienceByNumberAndFaculty() {
        when(audienceRepository.findByAudienceNumberAndFaculty(123, audience.getFaculty()))
                .thenReturn(Optional.of(audience));

        Audience audienceToCompare = audienceService.findAudienceByNumberAndFaculty(123, facultyFKN);

        assert(audienceToCompare.getId().equals(1L));
        assert(audienceToCompare.getCapacity().equals(50L));
        assert(audienceToCompare.getFaculty().getName().equals("ФКН"));
    }

    @Test
    void getFreeAudienceByFaculty() {
    }
}