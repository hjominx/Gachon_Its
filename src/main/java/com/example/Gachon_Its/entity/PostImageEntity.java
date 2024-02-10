package com.example.Gachon_Its.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "postImage")
public class PostImageEntity extends BaseEntity{

    public void save(PostEntity postEntity, String originalFileName, String storedFileName) {
        this.postEntity = postEntity;
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postImageId;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    // board_table - board_file_table은 자식관계, 밑에는 공식처럼 외워두자
    // @ManyToOne(fetch = FetchType.LAZY) = 글이랑 파일 대응관계 -> 1:N 임
    // (글에는 여러개 파일 들어갈 수 있지만 같은 파일이 여러개 글에는 못 들어가니까)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")    // 외래키 말하는듯? 외래키 설정은 1:N 에서 N인 테이블에다가 하래
    private PostEntity postEntity;  // 이게 PostEntity의 mappedby로 들어감

}
