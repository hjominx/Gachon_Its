package com.example.Gachon_Its.timetable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DepartmentCode {
    SOFTWARE("CS2120"), AI("CS2130");

    private final String value;
}
