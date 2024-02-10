package com.example.Gachon_Its.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInputDTO {

    private String id;
    private String pw;

    public LoginInputDTO() {
    }

    public LoginInputDTO(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

    // 추가된 메서드
    public String getPassword() {
        return pw;
    }

    // Getter 및 Setter는 필요에 따라 확장 가능
}
