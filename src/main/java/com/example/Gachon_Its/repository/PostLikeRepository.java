package com.example.Gachon_Its.repository;

import com.example.Gachon_Its.entity.PostEntity;
import com.example.Gachon_Its.entity.PostLikeEntity;
import com.example.Gachon_Its.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Long> {
    boolean existsByPostEntityAndUserEntity(PostEntity postEntity, UserEntity userEntity);

    // 특정 PostEntity의 ID를 기준으로 PostLikeEntity의 개수를 세는 메소드
    long countLikesByPostEntityPostId(Long postId);
}
