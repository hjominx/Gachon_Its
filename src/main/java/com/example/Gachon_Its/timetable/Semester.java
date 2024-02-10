package com.example.Gachon_Its.timetable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Semester {
    FIRST(10), SUMMER(11), SECOND(20), WINTER(21);

    private final int value;

}
