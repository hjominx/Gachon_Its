package com.example.Gachon_Its.timetable;

import com.example.Gachon_Its.service.TimeTableService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TimeTableTest {

    private final TimeTableService timeTableService;

    @Autowired
    public TimeTableTest(TimeTableService timeTableService) {
        this.timeTableService = timeTableService;
    }

    @Test
    void fetchTimeTable() {
        ResponseEntity<Boolean> response = timeTableService.refreshTimeTableDatabase(
                CampusCode.GLOBAL,
                2024,
                Semester.FIRST,
                UniversityCode.IT,
                DepartmentCode.SOFTWARE,
                IsuCode.MAJOR
        );
        assertThat(response.getBody()).isEqualTo(true);
    }
}
