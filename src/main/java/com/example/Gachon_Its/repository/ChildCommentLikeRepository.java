package com.example.Gachon_Its.repository;

import com.example.Gachon_Its.entity.ChildCommentEntity;
import com.example.Gachon_Its.entity.ChildCommentLikeEntity;
import com.example.Gachon_Its.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChildCommentLikeRepository extends JpaRepository<ChildCommentLikeEntity, Long> {
    boolean existsByChildCommentEntityAndUserEntity(ChildCommentEntity childCommentEntity, UserEntity userEntity);
}
