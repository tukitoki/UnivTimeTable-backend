package ru.vsu.cs.timetable.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.cs.timetable.dto.audience.CreateAudienceRequest;
import ru.vsu.cs.timetable.dto.audience.CreateAudienceResponse;
import ru.vsu.cs.timetable.exception.AudienceException;
import ru.vsu.cs.timetable.exception.EquipmentException;
import ru.vsu.cs.timetable.mapper.AudienceMapper;
import ru.vsu.cs.timetable.entity.Audience;
import ru.vsu.cs.timetable.entity.Equipment;
import ru.vsu.cs.timetable.repository.AudienceRepository;
import ru.vsu.cs.timetable.repository.EquipmentRepository;
import ru.vsu.cs.timetable.service.AudienceService;
import ru.vsu.cs.timetable.service.FacultyService;
import ru.vsu.cs.timetable.service.UniversityService;

import java.util.LinkedHashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class AudienceServiceImpl implements AudienceService {

    private final AudienceRepository audienceRepository;
    private final EquipmentRepository equipmentRepository;
    private final UniversityService universityService;
    private final FacultyService facultyService;
    private final AudienceMapper audienceMapper;

    @Override
    public void createAudience(CreateAudienceRequest createAudienceRequest,
                               Long univId, Long facultyId) {
        var univ = universityService.findUnivById(univId);
        var faculty = facultyService.findFacultyById(facultyId);

        if (audienceRepository.findByAudienceNumberAndUniversityAndFaculty(createAudienceRequest.getAudienceNumber(),
                univ, faculty).isPresent()) {
            throw AudienceException.CODE.AUDIENCE_ALREADY_EXIST.get();
        }
        Set<Equipment> equipment = new LinkedHashSet<>();

        createAudienceRequest.getEquipments().forEach(equipmentName -> {
            var optionalEquipment = equipmentRepository.findByDisplayNameIgnoreCase(equipmentName);

            if (optionalEquipment.isEmpty()) {
                throw EquipmentException.CODE.EQUIPMENT_NOT_EXIST.get();
            }
            equipment.add(optionalEquipment.get());
        });

        Audience audience = audienceMapper.toEntity(createAudienceRequest, univ, faculty, equipment);
        audienceRepository.save(audience);
    }

    @Override
    public CreateAudienceResponse showCreateAudience() {
        var equipmentNames = equipmentRepository.findAll()
                .stream()
                .map(Equipment::getDisplayName)
                .toList();

        return CreateAudienceResponse.builder()
                .equipments(equipmentNames)
                .build();
    }
}
