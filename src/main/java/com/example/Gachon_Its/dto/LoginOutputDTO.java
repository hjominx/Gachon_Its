package com.example.Gachon_Its.dto;

import com.example.Gachon_Its.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginOutputDTO {

    private String id;

    public LoginOutputDTO() {
    }

    public LoginOutputDTO(String id) {
        this.id = id;
    }

    public static LoginOutputDTO fromUserEntity(UserEntity userEntity) {
        return new LoginOutputDTO(userEntity.getUserId());
    }

    // Getter 및 Setter는 필요에 따라 확장 가능
}
