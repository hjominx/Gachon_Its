package com.example.Gachon_Its.entity;

import com.example.Gachon_Its.dto.ChildCommentDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "childComment")
public class ChildCommentEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long childCommentId;

    @Column
    private String childCommentContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentId")
    private CommentEntity commentEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "childCommentEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ChildCommentLikeEntity> childCommentLikeEntityList = new ArrayList<>();

    public static ChildCommentEntity toSaveEntity(ChildCommentDTO childCommentDTO, CommentEntity commentEntity, UserEntity userEntity) {
        ChildCommentEntity childCommentEntity = new ChildCommentEntity();
        childCommentEntity.setChildCommentId(childCommentDTO.getChildCommentId());
        childCommentEntity.setChildCommentContents(childCommentDTO.getChildCommentContents());
        childCommentEntity.setCommentEntity(commentEntity);
        childCommentEntity.setUserEntity(userEntity);
        return childCommentEntity;
    }
}
