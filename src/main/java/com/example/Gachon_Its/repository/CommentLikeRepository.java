package com.example.Gachon_Its.repository;

import com.example.Gachon_Its.entity.CommentEntity;
import com.example.Gachon_Its.entity.CommentLikeEntity;
import com.example.Gachon_Its.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, Long> {
    boolean existsByCommentEntityAndUserEntity(CommentEntity commentEntity, UserEntity userEntity);
}
