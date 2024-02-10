package com.example.Gachon_Its.timetable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CampusCode {
    GLOBAL(20),MEDICAL(21);

    private final int value;

}
