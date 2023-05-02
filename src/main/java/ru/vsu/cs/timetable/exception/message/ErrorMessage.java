package ru.vsu.cs.timetable.exception.message;

import lombok.Builder;

@Builder
public record ErrorMessage(String code, String message) {
}
