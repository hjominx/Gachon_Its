package com.example.Gachon_Its.entity;

import com.example.Gachon_Its.dto.ChildCommentLikeDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "childCommentLike")
public class ChildCommentLikeEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long childCommentLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "childCommentId")
    private ChildCommentEntity childCommentEntity;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;

    public static ChildCommentLikeEntity toSaveEntity(ChildCommentLikeDTO childCommentLikeDTO, ChildCommentEntity childCommentEntity, UserEntity userEntity) {
        ChildCommentLikeEntity childCommentLikeEntity = new ChildCommentLikeEntity();
        childCommentLikeEntity.setChildCommentLikeId(childCommentLikeDTO.getChildCommentLikeId());
        childCommentLikeEntity.setChildCommentEntity(childCommentEntity);
        childCommentLikeEntity.setUserEntity(userEntity);
        return childCommentLikeEntity;
    }
}
