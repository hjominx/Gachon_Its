package com.example.Gachon_Its.repository;

import com.example.Gachon_Its.entity.PostImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface PostImageRepository extends JpaRepository<PostImageEntity, Long> {
}
