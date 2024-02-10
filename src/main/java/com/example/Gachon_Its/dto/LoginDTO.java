package com.example.Gachon_Its.dto;

import com.example.Gachon_Its.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {

    private String id; //adsf
    private String pw;

    // 수정된 메서드
    public LoginOutputDTO toLoginOutput() {
        // 여기에서 UserEntity로부터 다른 필요한 정보를 가져와서 LoginOutputDTO 객체를 생성합니다.
        // 예를 들어, UserEntity에 이름(name)이라는 필드가 있다면 아래와 같이 작성할 수 있습니다.
        String name = ""; // UserEntity에서 이름 정보 가져오기
        return new LoginOutputDTO(name); // LoginOutputDTO 생성자에 맞게 수정하지 않습니다.
    }

    public static LoginDTO fromUserEntity(UserEntity userEntity) {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setId(userEntity.getUserId());
        // 여기에서 패스워드는 보안상의 이유로 넣지 않는 것이 좋습니다.
        return loginDTO;
    }
}
