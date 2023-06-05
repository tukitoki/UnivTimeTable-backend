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
import ru.vsu.cs.timetable.entity.Audience;
import ru.vsu.cs.timetable.entity.Faculty;
import ru.vsu.cs.timetable.repository.AudienceRepository;
import ru.vsu.cs.timetable.repository.UniversityRepository;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class AudienceServiceImplTest {

    @Mock
    private AudienceRepository audienceRepository;
    @InjectMocks
    private AudienceServiceImpl audienceService;

    private Audience audience = new Audience();
    private Faculty faculty = new Faculty();

    @BeforeEach
    void setUp() {
        audience.setAudienceNumber(123);
        audience.setCapacity(50L);
        audience.setId(1L);
        audience.setFaculty(faculty);
        faculty.setId(1L);
        faculty.setName("ФКН");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createAudience() {

    }

    @Test
    void findAudienceByNumberAndFaculty() {
        when(audienceRepository.findByAudienceNumberAndFaculty(123, audience.getFaculty()))
                .thenReturn(Optional.of(audience));

        Audience audienceToCompare = audienceService.findAudienceByNumberAndFaculty(123, faculty);

        assert(audienceToCompare.getId().equals(1L));
        assert(audienceToCompare.getCapacity().equals(50L));
        assert(audienceToCompare.getFaculty().getName().equals("ФКН"));
    }

    @Test
    void getFreeAudienceByFaculty() {
    }
}