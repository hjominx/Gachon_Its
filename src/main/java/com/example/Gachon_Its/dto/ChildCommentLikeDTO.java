package com.example.Gachon_Its.dto;

import com.example.Gachon_Its.entity.ChildCommentEntity;
import com.example.Gachon_Its.entity.ChildCommentLikeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor  //기본 생성자
@AllArgsConstructor //모든 필드를 매개변수로 하는 생성자
public class ChildCommentLikeDTO {

    private Long childCommentLikeId;

    private Long childCommentId;

    private String userId;

    public static ChildCommentLikeDTO toSaveDTO(ChildCommentLikeEntity childCommentLikeEntity, Long childCommentId, String userId) {
        ChildCommentLikeDTO childCommentLikeDTO = new ChildCommentLikeDTO();
        childCommentLikeDTO.setChildCommentLikeId(childCommentLikeEntity.getChildCommentLikeId());
        childCommentLikeDTO.setChildCommentId(childCommentId);
        childCommentLikeDTO.setUserId(userId);
        return childCommentLikeDTO;
    }

}
