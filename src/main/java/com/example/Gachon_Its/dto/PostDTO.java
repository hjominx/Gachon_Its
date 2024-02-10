package com.example.Gachon_Its.dto;

import com.example.Gachon_Its.entity.PostEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor  //기본 생성자
@AllArgsConstructor //모든 필드를 매개변수로 하는 생성자
@ToString
public class PostDTO {

    public void save(PostEntity postEntity, String userId) {
        this.postId = postEntity.getPostId();
        this.category = postEntity.getCategory();
        this.userId = userId;
        this.postTitle = postEntity.getPostTitle();
        this.postContents = postEntity.getPostContents();
        this.createdTime = postEntity.getCreatedTime();
        this.updatedTime = postEntity.getUpdatedTime();
        this.pointForQ = postEntity.getPointForQ();
        this.fileAttached = postEntity.getFileAttached();
        if (this.fileAttached == 1) {
            this.originalFileName=postEntity.getPostImageEntityList().get(0).getOriginalFileName();
            this.storedFileName=postEntity.getPostImageEntityList().get(0).getStoredFileName();
        }
    }

    private Long postId;

    private String category;

    private String userId;

    private String postTitle;

    private String postContents;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    private MultipartFile postFile; //save.html에서 controller로 넘어올 때 파일 담는 용도

    private String originalFileName; // 원본 파일 이름

    private String storedFileName; //서버 저장용 파일 이름  왜 구분? -> 어제 파일 "내 사진" 올림, 오늘도 "내 사진" 올림 , 두 개가 혼동될 수 있음

    private int fileAttached; // 파일 첨부 여부(첨부 1, 아님 0) (boolean으로 하면 나중에 boardEntity할 때 귀찮음)

    private int pointForQ;

}
