package com.example.Gachon_Its.dto;

import com.example.Gachon_Its.entity.ChildCommentEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor  //기본 생성자
@AllArgsConstructor //모든 필드를 매개변수로 하는 생성자
public class ChildCommentDTO {

    private Long childCommentId;

    private String childCommentContents;

    private Long commentId;

    private String userId;

    private LocalDateTime CreatedTime;

    private LocalDateTime UpdatedTime;

    public static ChildCommentDTO toSaveDTO(ChildCommentEntity childCommentEntity, String userId, Long commentId) {
        ChildCommentDTO childCommentDTO = new ChildCommentDTO();
        childCommentDTO.setChildCommentId(childCommentEntity.getChildCommentId());
        childCommentDTO.setChildCommentContents(childCommentEntity.getChildCommentContents());
        childCommentDTO.setCommentId(commentId);
        childCommentDTO.setUserId(userId);
        childCommentDTO.setCreatedTime(childCommentEntity.getCreatedTime());
        childCommentDTO.setUpdatedTime(childCommentEntity.getUpdatedTime());
        return childCommentDTO;
    }
}
