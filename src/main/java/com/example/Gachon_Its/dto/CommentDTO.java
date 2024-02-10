package com.example.Gachon_Its.dto;

import com.example.Gachon_Its.entity.CommentEntity;
import com.example.Gachon_Its.entity.PostEntity;
import com.example.Gachon_Its.entity.UserEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor  //기본 생성자
@AllArgsConstructor //모든 필드를 매개변수로 하는 생성자
@ToString
public class CommentDTO {

    private Long commentId;

    private Long postId;

    private String userId;

    private String commentContents;

    private int isSelected; // 1 or 0

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    public void save(CommentEntity commentEntity){
        this.commentId = commentEntity.getCommentId();
        this.postId = commentEntity.getPostEntity().getPostId();
        this.userId = commentEntity.getUserEntity().getUserId();
        this.commentContents = commentEntity.getCommentContents();
        this.isSelected = commentEntity.getIsSelected();
        this.createdTime = commentEntity.getCreatedTime();
        this.updatedTime = commentEntity.getUpdatedTime();
    }

    public static CommentDTO toSaveDTO(CommentEntity commentEntity, String userId, Long postId) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentId(commentEntity.getCommentId());
        commentDTO.setPostId(postId);
        commentDTO.setUserId(userId);
        commentDTO.setCommentContents(commentEntity.getCommentContents());
        commentDTO.setIsSelected(commentEntity.getIsSelected());
        commentDTO.setCreatedTime(commentEntity.getCreatedTime());
        commentDTO.setUpdatedTime(commentEntity.getUpdatedTime());

        return commentDTO;
    }


}
