package com.example.Gachon_Its.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TimeTable {
    @Id
    private String subjectId;//학수번호

//    private int acceptablePeople;//정원
//    private String time;//시간
    private String professorName;//교수 이름
//    private String locationName;//강의 장소
//    private String isuName;//이수 구분
//    private String department;//해당 학과
    private String subjectName;//강의명
//    private String scoreIsu;//학점

}
