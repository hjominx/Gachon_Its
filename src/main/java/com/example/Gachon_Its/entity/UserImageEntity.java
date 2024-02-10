package com.example.Gachon_Its.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "userImage")
public class UserImageEntity extends BaseEntity {

    public void save(UserEntity userEntity, String originalFileName, String storedFileName) {
        this.userEntity = userEntity;
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userImageId;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    @OneToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;

}
