package ru.vsu.cs.timetable.mapper;

import org.springframework.stereotype.Component;
import ru.vsu.cs.timetable.dto.audience.CreateAudienceRequest;
import ru.vsu.cs.timetable.entity.Audience;
import ru.vsu.cs.timetable.entity.Equipment;
import ru.vsu.cs.timetable.entity.Faculty;
import ru.vsu.cs.timetable.entity.University;
import ru.vsu.cs.timetable.planner.model.PlanningAudience;

import java.util.Set;

@Component
public class AudienceMapper {

    public Audience toEntity(CreateAudienceRequest audienceRequest,
                             University univ, Faculty faculty, Set<Equipment> equipment) {


        return Audience.builder()
                .audienceNumber(audienceRequest.getAudienceNumber())
                .capacity(audienceRequest.getCapacity())
                .university(univ)
                .faculty(faculty)
                .equipments(equipment)
                .build();
    }

    public PlanningAudience toPlanningAudience(Audience audience) {
        return PlanningAudience.builder()
                .id(audience.getId())
                .audienceNumber(audience.getAudienceNumber())
                .capacity(audience.getCapacity())
                .equipments(audience.getEquipments())
                .build();
    }
}
