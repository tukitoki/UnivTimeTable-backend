package ru.vsu.cs.timetable.logic.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.timetable.exception.AudienceException;
import ru.vsu.cs.timetable.exception.EquipmentException;
import ru.vsu.cs.timetable.logic.service.AudienceService;
import ru.vsu.cs.timetable.logic.service.FacultyService;
import ru.vsu.cs.timetable.logic.service.MailService;
import ru.vsu.cs.timetable.logic.service.UniversityService;
import ru.vsu.cs.timetable.model.dto.audience.AudienceDto;
import ru.vsu.cs.timetable.model.dto.audience.AudienceResponse;
import ru.vsu.cs.timetable.model.dto.audience.CreateAudienceRequest;
import ru.vsu.cs.timetable.model.dto.week_time.DayTimes;
import ru.vsu.cs.timetable.model.entity.Audience;
import ru.vsu.cs.timetable.model.entity.Class;
import ru.vsu.cs.timetable.model.entity.Equipment;
import ru.vsu.cs.timetable.model.entity.Faculty;
import ru.vsu.cs.timetable.model.enums.DayOfWeekEnum;
import ru.vsu.cs.timetable.model.enums.WeekType;
import ru.vsu.cs.timetable.model.mapper.AudienceMapper;
import ru.vsu.cs.timetable.repository.AudienceRepository;
import ru.vsu.cs.timetable.repository.EquipmentRepository;
import ru.vsu.cs.timetable.utils.TimeUtils;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class AudienceServiceImpl implements AudienceService {

    private final UniversityService universityService;
    private final FacultyService facultyService;
    private final MailService mailService;
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

        if (createAudienceRequest.getEquipments() != null) {
            createAudienceRequest.getEquipments().forEach(equipmentName -> {
                var optionalEquipment = equipmentRepository.findByDisplayNameIgnoreCase(equipmentName);

                if (optionalEquipment.isEmpty()) {
                    throw EquipmentException.CODE.EQUIPMENT_NOT_EXIST.get();
                }
                equipment.add(optionalEquipment.get());
            });
        }

        Audience audience = audienceMapper.toEntity(createAudienceRequest, univ, faculty, equipment);

        audience = audienceRepository.save(audience);

        log.info("audience was saved {}", audience);
    }

    @Override
    public AudienceDto getAudienceById(Long id) {
        Audience audience = audienceRepository.findById(id)
                .orElseThrow(AudienceException.CODE.ID_NOT_FOUND::get);

        return audienceMapper.toDto(audience);
    }

    @Override
    public void deleteAudience(Long id) {
        Audience audience = audienceRepository.findById(id)
                .orElseThrow(AudienceException.CODE.ID_NOT_FOUND::get);

        var classes = audience.getClasses();
        classes.forEach(it -> {
            it.setAudience(null);
            mailService.sendAudienceWasDeleted(it.getLecturer().getEmail());
        });

        audienceRepository.deleteById(id);

        log.info("audience {} was successfully deleted", audience);
    }

    @Override
    public void updateAudience(AudienceDto audienceDto, Long id) {
        Audience oldAudience = audienceRepository.findById(id)
                .orElseThrow(AudienceException.CODE.ID_NOT_FOUND::get);

        var equipments = new HashSet<Equipment>();
        if (audienceDto.getEquipments() != null) {
            audienceDto.getEquipments().forEach(equipName -> {
                var equipment = equipmentRepository.findByDisplayNameIgnoreCase(equipName)
                        .orElseThrow(EquipmentException.CODE.EQUIPMENT_NOT_EXIST::get);
                equipments.add(equipment);
            });
        }

        Audience newAudience = audienceMapper.toEntity(audienceDto, equipments);

        var optionalAudience = audienceRepository.findByAudienceNumberAndFaculty(audienceDto.getAudienceNumber(), oldAudience.getFaculty());
        if (optionalAudience.isPresent() && !oldAudience.getAudienceNumber().equals(audienceDto.getAudienceNumber())) {
            throw AudienceException.CODE.AUDIENCE_ALREADY_EXIST.get();
        }

        var changedEquipments = oldAudience.getEquipments().equals(equipments)
                ? null
                : equipments.stream()
                .map(Equipment::getDisplayName)
                .collect(Collectors.toSet());
        var changedNumber = oldAudience.getAudienceNumber().equals(newAudience.getAudienceNumber())
                ? null
                : newAudience.getAudienceNumber();
        var changedCapacity = oldAudience.getCapacity().equals(newAudience.getCapacity())
                ? null
                : newAudience.getCapacity();

        BeanUtils.copyProperties(newAudience, oldAudience, "id", "faculty", "university", "classes");

        oldAudience = audienceRepository.save(oldAudience);

        if (!oldAudience.getClasses().isEmpty()) {
            oldAudience.getClasses().forEach(it -> {
                mailService.sendAudienceWasUpdated(
                        it.getLecturer().getEmail(),
                        changedEquipments,
                        changedNumber,
                        changedCapacity
                );
            });
        }

        log.info("audience {} was successfully updated", oldAudience);
    }

    @Override
    public List<AudienceResponse> getAudiencesByFaculty(Long facultyId) {
        var faculty = facultyService.findFacultyById(facultyId);
        var facultyAudiences = audienceRepository.findAllByFaculty(faculty);

        return facultyAudiences.stream()
                .map(audienceMapper::toResponse)
                .toList();
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
    public Map<Audience, List<DayTimes>> getFreeAudiencesByFaculty(Faculty faculty) {
        var facultyAudiences = audienceRepository.findAllByFaculty(faculty);
        Map<Audience, List<DayTimes>> audiencesFreeTime = new HashMap<>();

        facultyAudiences.forEach(audience -> {
            List<DayTimes> dayTimes = new ArrayList<>();
            for (var day : DayOfWeekEnum.values()) {
                if (day == DayOfWeekEnum.SUNDAY) {
                    continue;
                }

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
