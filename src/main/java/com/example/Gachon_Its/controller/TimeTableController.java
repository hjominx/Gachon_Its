package com.example.Gachon_Its.controller;

import com.example.Gachon_Its.service.TimeTableService;
import com.example.Gachon_Its.timetable.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TimeTableController {

    private final TimeTableService timeTableService;

    @GetMapping("timetable")
    public ResponseEntity<Boolean> update() {
        return timeTableService.refreshTimeTableDatabase(
                CampusCode.GLOBAL,
                2024,
                Semester.FIRST,
                UniversityCode.IT,
                DepartmentCode.SOFTWARE,
                IsuCode.MAJOR
        );
    }
}
