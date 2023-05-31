package ru.vsu.cs.timetable.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.timetable.dto.audience.CreateAudienceRequest;
import ru.vsu.cs.timetable.dto.week_time.DayTimes;
import ru.vsu.cs.timetable.entity.Audience;
import ru.vsu.cs.timetable.entity.Class;
import ru.vsu.cs.timetable.entity.Equipment;
import ru.vsu.cs.timetable.entity.Faculty;
import ru.vsu.cs.timetable.entity.enums.DayOfWeekEnum;
import ru.vsu.cs.timetable.entity.enums.WeekType;
import ru.vsu.cs.timetable.exception.AudienceException;
import ru.vsu.cs.timetable.exception.EquipmentException;
import ru.vsu.cs.timetable.mapper.AudienceMapper;
import ru.vsu.cs.timetable.repository.AudienceRepository;
import ru.vsu.cs.timetable.repository.EquipmentRepository;
import ru.vsu.cs.timetable.service.AudienceService;
import ru.vsu.cs.timetable.service.FacultyService;
import ru.vsu.cs.timetable.service.UniversityService;
import ru.vsu.cs.timetable.utils.TimeUtils;

import java.time.LocalTime;
import java.util.*;

@RequiredArgsConstructor
@Transactional
@Service
public class AudienceServiceImpl implements AudienceService {

    private final UniversityService universityService;
    private final FacultyService facultyService;
    private final AudienceMapper audienceMapper;
    private final AudienceRepository audienceRepository;
    private final EquipmentRepository equipmentRepository;

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
    @Transactional(readOnly = true)
    public Audience findAudienceByNumberAndFaculty(Integer audienceNumber, Faculty faculty) {
        return audienceRepository.findByAudienceNumberAndFaculty(audienceNumber, faculty)
                .orElseThrow(AudienceException.CODE.AUDIENCE_FACULTY_NUMBER_NOT_FOUND::get);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAvailableEquipments() {
        return equipmentRepository.findAll()
                .stream()
                .map(Equipment::getDisplayName)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Audience, List<DayTimes>> getFreeAudienceByFaculty(Faculty faculty) {
        var facultyAudiences = audienceRepository.findAllByFaculty(faculty);
        Map<Audience, List<DayTimes>> audiencesFreeTime = new HashMap<>();

        facultyAudiences.forEach(audience -> {
            List<DayTimes> dayTimes = new ArrayList<>();
            for (var day : DayOfWeekEnum.values()) {
                Map<WeekType, List<LocalTime>> weekTimes = new HashMap<>();
                for (var weekType : WeekType.values()) {
                    List<LocalTime> audienceFreeTime = TimeUtils.getPossibleClassTimes();
                    List<LocalTime> classesTimes = audience.getClasses().stream()
                            .filter(aClass -> aClass.getDayOfWeek() == day
                                    && aClass.getWeekType() == weekType)
                            .map(Class::getStartTime)
                            .toList();

                    audienceFreeTime.removeAll(classesTimes);
                    weekTimes.put(weekType, audienceFreeTime);
                }
                dayTimes.add(DayTimes.builder()
                        .dayOfWeek(day)
                        .weekTimes(weekTimes)
                        .build());
            }

            audiencesFreeTime.put(audience, dayTimes);
        });

        return audiencesFreeTime;
    }
}
