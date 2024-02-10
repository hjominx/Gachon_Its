package com.example.Gachon_Its.repository;

import com.example.Gachon_Its.entity.ChildCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ChildCommentRepository extends JpaRepository<ChildCommentEntity, Long> {
}
