package ru.vsu.cs.timetable.logic.planner.utils;

import ru.vsu.cs.timetable.logic.planner.model.PlanningClass;

public class HardViolationTemplateUtil {

    public static String audienceConflict(PlanningClass planningClass, PlanningClass planningClass2) {
        return "Конфликт аудитории №%d, пары: %s, с парой: %s"
                .formatted(planningClass.getAudience().getAudienceNumber(), planningClass, planningClass2);
    }

    public static String lecturerConflict(PlanningClass planningClass, PlanningClass planningClass2) {
        return "Конфликт преподователей. Конфликт пары: %s, с парой %s"
                .formatted(planningClass, planningClass2);
    }

    public static String studentGroupConflict(PlanningClass planningClass, PlanningClass planningClass2) {
        return "Конфликт групп. Конфликт возможной пары: %s с возможной парой: %s"
                .formatted(planningClass, planningClass2);
    }
}
