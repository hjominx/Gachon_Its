package com.example.Gachon_Its.timetable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum IsuCode {
    LIBERAL_ART("001"),
    MAJOR("002"),
    MILITARY("003"),
    TEACHING("004"),
    SPECIAL("005"),
    GENERAL_SELECT("006");

    private final String value;
}
