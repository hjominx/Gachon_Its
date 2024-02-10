package com.example.Gachon_Its.dto;

import com.example.Gachon_Its.entity.PostEntity;
import com.example.Gachon_Its.entity.PostLikeEntity;
import com.example.Gachon_Its.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor  //기본 생성자
@AllArgsConstructor //모든 필드를 매개변수로 하는 생성자
public class PostLikeDTO {

    private Long postLikeId;

    private String userId;

    private Long postId;

    public static PostLikeDTO toSaveDTO(PostLikeEntity postLikeEntity, UserEntity userEntity, PostEntity postEntity) {
        PostLikeDTO postLikeDTO = new PostLikeDTO();
        postLikeDTO.setPostLikeId(postLikeEntity.getPostLikeId());
        postLikeDTO.setUserId(userEntity.getUserId());
        postLikeDTO.setPostId(postEntity.getPostId());
        return postLikeDTO;
    }
}



/*
    package com.example.Gachon_Its.service;

import com.example.Gachon_Its.dto.CommentDTO;
import com.example.Gachon_Its.dto.CommentLikeDTO;
import com.example.Gachon_Its.entity.CommentEntity;
import com.example.Gachon_Its.entity.CommentLikeEntity;
import com.example.Gachon_Its.entity.PostEntity;
import com.example.Gachon_Its.entity.UserEntity;
import com.example.Gachon_Its.repository.CommentLikeRepository;
import com.example.Gachon_Its.repository.CommentRepository;
import com.example.Gachon_Its.repository.PostRepository;
import com.example.Gachon_Its.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentDTO createComment(CommentDTO commentDTO) {
        // DTO에서 Entity로 변환
        CommentEntity commentEntity = commentDTO.toEntity();

        // DB에 저장하고 반환
        commentEntity = commentRepository.save(commentEntity);

        // Entity를 DTO로 변환하여 반환
        return CommentDTO.toSaveDTO(commentEntity);
    }

    // 나머지 메서드들은 동일하게 유지
}

 */