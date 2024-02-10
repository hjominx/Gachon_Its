package com.example.Gachon_Its.dto;

import com.example.Gachon_Its.entity.CommentLikeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor  //기본 생성자
@AllArgsConstructor //모든 필드를 매개변수로 하는 생성자
public class CommentLikeDTO {

    private Long commentLikeId;

    private String userId;

    private Long commentId;

}
