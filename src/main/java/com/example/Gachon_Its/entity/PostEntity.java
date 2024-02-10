package com.example.Gachon_Its.entity;

import com.example.Gachon_Its.dto.PostDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "post")
@AllArgsConstructor
@NoArgsConstructor
public class PostEntity extends BaseEntity{

    public void save(PostDTO postDTO, UserEntity userEntity) {
        this.category = postDTO.getCategory();
        this.postTitle = postDTO.getPostTitle();
        this.postContents = postDTO.getPostContents();
        this.fileAttached = postDTO.getFileAttached();
        this.pointForQ = postDTO.getPointForQ();
        this.userEntity = userEntity;
    }

    public void update(PostDTO postDTO, UserEntity userEntity) {
        this.postId = postDTO.getPostId();
        save(postDTO, userEntity);
    }

    @Id     //pk(primary key)로 지정 , DB의 식별자
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private Long postId;

    @Column
    private String category;//과목 학수 번호

    @Column
    private String postTitle;

    @Column(length = 500)
    private String postContents;

    @Column
    private int fileAttached; // 1 or 0

    @Column
    private int pointForQ;  // 내공 0 -> This post is not a question post.

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "postEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PostImageEntity> postImageEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "postEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "postEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PostLikeEntity> postLikeEntityList = new ArrayList<>();

}
