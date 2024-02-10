package com.example.Gachon_Its.entity;

import com.example.Gachon_Its.dto.PostLikeDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "postLike")
public class PostLikeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postLikeId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "postId")
    private PostEntity postEntity;

    public static PostLikeEntity toSaveEntity(PostLikeDTO postLikeDTO,UserEntity userEntity, PostEntity postEntity) {
        PostLikeEntity postLikeEntity = new PostLikeEntity();
        postLikeEntity.setPostLikeId(postLikeEntity.getPostLikeId());
        postLikeEntity.setUserEntity(userEntity);
        postLikeEntity.setPostEntity(postEntity);
        return postLikeEntity;
    }

}
