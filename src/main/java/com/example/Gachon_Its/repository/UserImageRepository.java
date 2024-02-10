package com.example.Gachon_Its.repository;

import com.example.Gachon_Its.entity.UserImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserImageRepository extends JpaRepository<UserImageEntity, Long> {
}
