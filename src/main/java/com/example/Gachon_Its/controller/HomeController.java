package com.example.Gachon_Its.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Get: 리소스 조회 요청
// Post: 리소스 생성, 업데이트

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(){
        return "home";
    }
}
