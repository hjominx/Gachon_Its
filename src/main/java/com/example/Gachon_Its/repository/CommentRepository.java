package com.example.Gachon_Its.repository;

import com.example.Gachon_Its.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByUserEntityUserId(String userId);


    List<CommentEntity> findByPostEntityPostId(Long postId);

    // 특정 게시물 ID에 대한 댓글 수를 반환하는 메소드
    long countCommentsByPostEntityPostId(Long postId);

}
