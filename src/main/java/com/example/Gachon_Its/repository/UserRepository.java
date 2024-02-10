package com.example.Gachon_Its.repository;

import com.example.Gachon_Its.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface UserRepository extends JpaRepository<UserEntity, String> {

    @Modifying
    @Query(value = "update UserEntity u set u.qPoint=u.qPoint - :pointForQ where u.userId=:userId")
    void updateQpoint(@Param("userId") String userId, @Param("pointForQ") int pointForQ);
}
