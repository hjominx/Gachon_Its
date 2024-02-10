package com.example.Gachon_Its.entity;

import com.example.Gachon_Its.dto.UserDTO;
import com.example.Gachon_Its.tier.UserTier;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user")
public class UserEntity extends BaseEntity{
    public void save(UserDTO userDTO) {
        this.userId = userDTO.getUserId();
        this.password = userDTO.getPassword();
        this.userName = userDTO.getUserName();
        this.nickName = userDTO.getNickName();
        this.email = userDTO.getEmail();
        this.qPoint = userDTO.getQPoint();
        this.aPoint = userDTO.getAPoint();
        this.fileAttached = userDTO.getFileAttached();
    }

    public void newUser(UserDTO userDTO) {
        save(userDTO);
        this.qPoint = 100;
        this.aPoint = 0;
    }


    @Getter
    @Id
    private String userId;

    @Column
    private String password;

    @Column
    private String userName;

    @Column
    private String nickName;

    @Column
    private String email;

    @Column
    private int fileAttached;   // 1 or 0

    @Column
    private int qPoint;

    @Column
    private int aPoint;

    @Enumerated
    private UserTier userTier;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PostEntity> postEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private UserImageEntity userImageEntity;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PostLikeEntity> postLikeEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentLikeEntity> commentLikeEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ChildCommentEntity> childCommentEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ChildCommentLikeEntity> childCommentLikeEntityList = new ArrayList<>();

}
