package com.example.Gachon_Its.service;

import com.example.Gachon_Its.entity.TimeTable;
import com.example.Gachon_Its.repository.TimeTableRepository;
import com.example.Gachon_Its.timetable.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeTableService {

    private final TimeTableRepository timeTableRepository;

    @Transactional
    public ResponseEntity<Boolean> refreshTimeTableDatabase(CampusCode campusCode, int year, Semester semester, UniversityCode universityCode, DepartmentCode departmentCode, IsuCode isuCode) {
        List<TimeTable> timeTableList = fetchTimeTable(campusCode, year, semester, universityCode, departmentCode, isuCode);
        if (timeTableList == null || timeTableList.isEmpty()) return ResponseEntity.noContent().build();

        timeTableRepository.saveAll(timeTableList);

        return ResponseEntity.ok(true);
    }

    public List<TimeTable> getTimeTableList(){
        return timeTableRepository.findAll();
    }

    private List<TimeTable> fetchTimeTable(CampusCode campusCode, int year, Semester semester, UniversityCode universityCode, DepartmentCode departmentCode, IsuCode isuCode) {
        //Post request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("groupType", String.valueOf(campusCode.getValue()));
        body.add("searchYear", String.valueOf(year));
        body.add("searchTerm", String.valueOf(semester.getValue()));
        body.add("searchUnivCD", String.valueOf(universityCode.getValue()));
        body.add("searchDeptCD", departmentCode.getValue());
        body.add("searchIsuCD", isuCode.getValue());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(
                "https://info.gachon.ac.kr/Ssu1000q/mainSearch.do",
                HttpMethod.POST,
                entity,
                String.class
        );

        if (!response.hasBody()) return null;

        JsonObject jsonObject = JsonParser.parseString(response.getBody()).getAsJsonObject();
        if (jsonObject == null || jsonObject.isJsonNull()) return null;

        //Data parsing
        List<TimeTable> timeTableList = new ArrayList<>();
        jsonObject.get("MainData").getAsJsonArray().forEach(element -> {
            JsonObject data = element.getAsJsonObject();
            TimeTable timeTable = new TimeTable(
                    data.get("HAKSU_NO").getAsString(),
                    //data.get("APP_PEOPLE").getAsInt(),
                    //data.get("TIME").getAsString(),
                    data.get("PROFNM").isJsonNull() ? "미지정" : data.get("PROFNM").getAsString(),
                    //data.get("LOC_NM").getAsString(),
                    //data.get("ISU_NM").getAsString(),
                    //data.get("PRINT_DPT").getAsString(),
                    data.get("SUBJECT_NM_KOR").getAsString()
                    //data.get("SISU").getAsString()
            );
            timeTableList.add(timeTable);
        });

        return timeTableList;
    }

}
