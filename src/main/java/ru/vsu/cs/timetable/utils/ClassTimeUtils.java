package ru.vsu.cs.timetable.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

@UtilityClass
public final class ClassTimeUtils {

    public static final LocalTime START_TIME = LocalTime.of(8, 0);
    public static final int INTERVAL_MINUTES = 90;
    public static final int ACADEMICAL_PAIR_MINUTES = 45;
    public static final int LAST_PAIR_INTERVAL_MINUTE = 80;
    public static final int BREAK_MINUTES = 10;
    public static final int PAIR_BREAK_MINUTES = 5;
    public static final int BIG_BREAK_MINUTES = 20;
    public static final int MAX_NUM_OF_PAIRS = 8;

    public static List<LocalTime> getPossibleClassTimes() {
        List<LocalTime> possibleTimes = new LinkedList<>();
        possibleTimes.add(START_TIME);
        int summaryBreakTime = 0;
        for (int i = 1; i < MAX_NUM_OF_PAIRS; i++) {
            int timesToAdd;
            if (i == 3) {
                summaryBreakTime += BIG_BREAK_MINUTES + PAIR_BREAK_MINUTES;
            } else if (i < 7) {
                summaryBreakTime += BREAK_MINUTES + PAIR_BREAK_MINUTES;
            }
            timesToAdd = INTERVAL_MINUTES * i + summaryBreakTime;
            possibleTimes.add(START_TIME.plusMinutes(timesToAdd));
        }
        return possibleTimes;
    }

    public static LocalTime calculateLastTimeByStart(LocalTime startTime) {
        if (startTime.equals(LocalTime.of(18, 40))
                || startTime.equals(LocalTime.of(20, 10))) {
            return startTime.plusMinutes(LAST_PAIR_INTERVAL_MINUTE);
        }
        return startTime.plusMinutes(INTERVAL_MINUTES + PAIR_BREAK_MINUTES);
    }
}
