package com.example.Gachon_Its.dto;

import com.example.Gachon_Its.entity.UserEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor  //기본 생성자
@AllArgsConstructor //모든 필드를 매개변수로 하는 생성자
@ToString
public class UserDTO {

    public void save(UserEntity userEntity) {
        this.userId = userEntity.getUserId();
        this.password = userEntity.getPassword();
        this.userName = userEntity.getUserName();
        this.nickName = userEntity.getNickName();
        this.email = userEntity.getEmail();
        this.qPoint = userEntity.getQPoint();
        this.aPoint = userEntity.getAPoint();
        this.fileAttached = userEntity.getFileAttached();
        if (this.fileAttached == 1) {
            this.originalFileName = userEntity.getUserImageEntity().getOriginalFileName();
            this.storedFileName = userEntity.getUserImageEntity().getStoredFileName();
        }
    }

    private String userId;

    private String password;

    private String userName;

    private String nickName;

    private String email;

    private MultipartFile userFile; //save.html에서 controller로 넘어올 때 파일 담는 용도

    private String originalFileName; // 원본 파일 이름

    private String storedFileName; //서버 저장용 파일 이름  왜 구분? -> 어제 파일 "내 사진" 올림, 오늘도 "내 사진" 올림 , 두 개가 혼동될 수 있음

    private int fileAttached;

    private int qPoint;

    private int aPoint;

}
