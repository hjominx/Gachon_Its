package com.example.Gachon_Its.service;

import com.example.Gachon_Its.dto.ChildCommentDTO;
import com.example.Gachon_Its.dto.CommentDTO;
import com.example.Gachon_Its.entity.ChildCommentEntity;
import com.example.Gachon_Its.entity.CommentEntity;
import com.example.Gachon_Its.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final ChildCommentRepository childCommentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentDTO createComment(CommentDTO commentDTO) {
        // CommentEntity 인스턴스 생성
        CommentEntity commentEntity = new CommentEntity();

        // CommentEntity.toSaveEntity 메소드 호출 시, 올바른 타입을 전달하도록 수정
        commentEntity = commentEntity.toSaveEntity(commentDTO,
                userRepository.findById(commentDTO.getUserId())
                        .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다.")),
                postRepository.findById(commentDTO.getPostId())
                        .orElseThrow(() -> new RuntimeException("포스트를 찾을 수 없습니다."))
        );

        // 댓글 저장
        commentEntity = commentRepository.save(commentEntity);

        // 댓글을 DTO로 변환하여 반환
        return CommentDTO.toSaveDTO(commentEntity, commentEntity.getUser().getUserId(), commentEntity.getPost().getPostId());
    }


    @Transactional
    public ChildCommentDTO createChildComment(Long commentId, ChildCommentDTO childCommentDTO) {
        // 부모 댓글 가져오기
        CommentEntity parentComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("부모 댓글을 찾을 수 없습니다."));

        // ChildCommentEntity 인스턴스 생성 및 저장
        ChildCommentEntity childCommentEntity = ChildCommentEntity.toSaveEntity(childCommentDTO, parentComment,
                userRepository.findById(childCommentDTO.getUserId())
                        .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다.")));

        childCommentEntity = childCommentRepository.save(childCommentEntity);

        // 부모 댓글의 대댓글 목록에 추가
        CommentEntity.addChildComment(childCommentEntity);

        return ChildCommentDTO.toSaveDTO(childCommentEntity, childCommentEntity.getUserEntity().getUserId(), commentId);
    }

    @Transactional
    public CommentDTO updateComment(Long commentId, CommentDTO commentDTO) {
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        commentEntity.setCommentContents(commentDTO.getCommentContents());
        commentEntity.setIsSelected(commentDTO.getIsSelected());
        commentEntity = commentRepository.save(commentEntity);

        return CommentDTO.toSaveDTO(commentEntity, commentDTO.getUserId(), commentDTO.getPostId());
    }

    @Transactional(readOnly = true)
    public CommentDTO getCommentById(Long commentId) {
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        return CommentDTO.toSaveDTO(commentEntity, commentEntity.getUser().getUserId(), commentEntity.getPost().getPostId());
    }

    @Transactional
    public void deleteComment(Long commentId) {
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        commentRepository.delete(commentEntity);
    }
    //countComments 메소드 추가
    @Transactional(readOnly = true)
    public Long countComments(Long postId) {
        return commentRepository.countCommentsByPostEntityPostId(postId);
    }

//    @Transactional
//    public CommentLikeDTO likeComment(Long commentId, CommentLikeDTO commentLikeDTO) {
//        CommentEntity commentEntity = commentRepository.findById(commentId)
//                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
//
//        Optional<UserEntity> optionalUserEntity = userRepository.findById(commentLikeDTO.getUserId());
//        UserEntity userEntity = optionalUserEntity.orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
//
//        CommentLikeEntity commentLikeEntity = new CommentLikeEntity();
//        commentLikeEntity.setCommentEntity(commentEntity);
//        commentLikeEntity.setUserEntity(userEntity);
//        commentLikeEntity = commentLikeRepository.save(commentLikeEntity);
//
//        return CommentLikeDTO.toSaveDTO(commentLikeEntity, commentLikeDTO.getUserId(), commentId);
//    }
//
//    @Transactional
//    public void unlikeComment(Long commentId, Long likeId) {
//        CommentEntity commentEntity = commentRepository.findById(commentId)
//                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
//
//        Optional<CommentLikeEntity> optionalCommentLikeEntity = commentLikeRepository.findById(likeId);
//        CommentLikeEntity commentLikeEntity = optionalCommentLikeEntity.orElseThrow(() -> new RuntimeException("좋아요를 찾을 수 없습니다."));
//
//        commentLikeRepository.delete(commentLikeEntity);
//    }
}
