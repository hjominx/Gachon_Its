package com.example.Gachon_Its.service;

import com.example.Gachon_Its.dto.ChildCommentLikeDTO;
import com.example.Gachon_Its.dto.CommentLikeDTO;
import com.example.Gachon_Its.dto.PostLikeDTO;
import com.example.Gachon_Its.entity.*;
import com.example.Gachon_Its.repository.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LikeService {

    private final CommentRepository commentRepository;
    private final ChildCommentRepository childCommentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final ChildCommentLikeRepository childCommentLikeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;  // 새로운 repository 추가

    @Transactional
    public ResponseEntity<CommentLikeDTO> likeComment(Long commentId, CommentLikeDTO commentLikeDTO) {
        Optional<CommentEntity> commentEntityOptional = commentRepository.findById(commentId);
        if (!commentEntityOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        CommentEntity commentEntity = commentEntityOptional.get();  // Optional에서 실제 객체를 가져옵니다.

        Optional<UserEntity> optionalUserEntity = userRepository.findById(commentLikeDTO.getUserId());
        UserEntity userEntity = optionalUserEntity.orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        // 이미 해당 유저가 해당 댓글에 좋아요를 눌렀는지 확인
        boolean alreadyLiked = commentLikeRepository.existsByCommentEntityAndUserEntity(commentEntity, userEntity);

        if (!alreadyLiked) {
            // 좋아요를 누른 적이 없는 경우에만 좋아요 저장
            CommentLikeEntity commentLikeEntity = new CommentLikeEntity();
            commentLikeEntity.setCommentEntity(commentEntity);  // Optional에서 꺼낸 실제 객체를 전달합니다.
            commentLikeEntity.setUserEntity(userEntity);
            commentLikeEntity = commentLikeRepository.save(commentLikeEntity);

            // CommentLikeDTO를 직접 생성하여 값을 설정
            CommentLikeDTO createdCommentLikeDTO = new CommentLikeDTO();
            createdCommentLikeDTO.setCommentLikeId(commentLikeEntity.getCommentLikeId());
            createdCommentLikeDTO.setUserId(commentLikeEntity.getUserEntity().getUserId());
            createdCommentLikeDTO.setCommentId(commentLikeEntity.getCommentEntity().getCommentId());

            return ResponseEntity.ok(createdCommentLikeDTO);
        } else {
            // 이미 좋아요를 누른 경우에는 예외 처리
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
        }
    }


    @Transactional
    public void unlikeComment(Long commentId, Long likeId) {
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        Optional<CommentLikeEntity> optionalCommentLikeEntity = commentLikeRepository.findById(likeId);
        CommentLikeEntity commentLikeEntity = optionalCommentLikeEntity.orElseThrow(() -> new RuntimeException("좋아요를 찾을 수 없습니다."));

        // 좋아요 취소
        commentLikeRepository.delete(commentLikeEntity);
    }

    public boolean getLikeStatus(Long commentId, String userId) {

        Optional<CommentEntity> optionalCommentEntity = commentRepository.findById(commentId);
        CommentEntity commentEntity = optionalCommentEntity.orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        UserEntity userEntity = optionalUserEntity.orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        boolean likeStatus = commentLikeRepository.existsByCommentEntityAndUserEntity(commentEntity, userEntity);

        return likeStatus;
    }

    // 대댓글 좋아요 추가
    @Transactional
    public ResponseEntity<ChildCommentLikeDTO> likeChildComment(Long childCommentId, ChildCommentLikeDTO childCommentLikeDTO) {
        Optional<ChildCommentEntity> childCommentEntityOptional = childCommentRepository.findById(childCommentId);
        if (!childCommentEntityOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ChildCommentEntity childCommentEntity = childCommentEntityOptional.get();

        Optional<UserEntity> optionalUserEntity = userRepository.findById(childCommentLikeDTO.getUserId());
        UserEntity userEntity = optionalUserEntity.orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        // 이미 해당 유저가 해당 대댓글에 좋아요를 눌렀는지 확인
        boolean alreadyLiked = childCommentLikeRepository.existsByChildCommentEntityAndUserEntity(childCommentEntity, userEntity);

        if (!alreadyLiked) {
            // 좋아요를 누른 적이 없는 경우에만 좋아요 저장
            ChildCommentLikeEntity childCommentLikeEntity = new ChildCommentLikeEntity();
            childCommentLikeEntity.setChildCommentEntity(childCommentEntity);
            childCommentLikeEntity.setUserEntity(userEntity);
            childCommentLikeEntity = childCommentLikeRepository.save(childCommentLikeEntity);

            ChildCommentLikeDTO createdChildCommentLikeDTO = new ChildCommentLikeDTO();
            createdChildCommentLikeDTO.setChildCommentLikeId(childCommentLikeEntity.getChildCommentLikeId());
            createdChildCommentLikeDTO.setUserId(childCommentLikeEntity.getUserEntity().getUserId());
            createdChildCommentLikeDTO.setChildCommentId(childCommentLikeEntity.getChildCommentEntity().getChildCommentId());

            return ResponseEntity.ok(createdChildCommentLikeDTO);
        } else {
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
        }
    }

    // 대댓글 좋아요 취소
    @Transactional
    public void unlikeChildComment(Long childCommentId, Long likeId) {
        ChildCommentEntity childCommentEntity = childCommentRepository.findById(childCommentId)
                .orElseThrow(() -> new RuntimeException("대댓글을 찾을 수 없습니다."));

        Optional<ChildCommentLikeEntity> optionalChildCommentLikeEntity = childCommentLikeRepository.findById(likeId);
        ChildCommentLikeEntity childCommentLikeEntity = optionalChildCommentLikeEntity.orElseThrow(() -> new RuntimeException("좋아요를 찾을 수 없습니다."));

        childCommentLikeRepository.delete(childCommentLikeEntity);
    }

    // 대댓글 좋아요 상태 가져오기
    public boolean getChildCommentLikeStatus(Long childCommentId, String userId) {
        Optional<ChildCommentEntity> optionalChildCommentEntity = childCommentRepository.findById(childCommentId);
        ChildCommentEntity childCommentEntity = optionalChildCommentEntity.orElseThrow(() -> new RuntimeException("대댓글을 찾을 수 없습니다."));

        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        UserEntity userEntity = optionalUserEntity.orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        return childCommentLikeRepository.existsByChildCommentEntityAndUserEntity(childCommentEntity, userEntity);
    }

    // 게시글 좋아요 추가
    @Transactional
    public ResponseEntity<Object> likePost(Long postId, PostLikeDTO postLikeDTO) {
        Optional<PostEntity> postEntityOptional = postRepository.findById(postId);
        if (!postEntityOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        PostEntity postEntity = postEntityOptional.get();

        Optional<UserEntity> optionalUserEntity = userRepository.findById(postLikeDTO.getUserId());
        UserEntity userEntity = optionalUserEntity.orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        // 이미 해당 유저가 해당 게시글에 좋아요를 눌렀는지 확인
        boolean alreadyLiked = postLikeRepository.existsByPostEntityAndUserEntity(postEntity, userEntity);

        if (!alreadyLiked) {
            // 좋아요를 누른 적이 없는 경우에만 좋아요 저장
            PostLikeEntity postLikeEntity = new PostLikeEntity();
            postLikeEntity.setPostEntity(postEntity);
            postLikeEntity.setUserEntity(userEntity);
            postLikeEntity = postLikeRepository.save(postLikeEntity);

            PostLikeDTO createdPostLikeDTO = new PostLikeDTO();
            createdPostLikeDTO.setPostLikeId(postLikeEntity.getPostLikeId());
            createdPostLikeDTO.setUserId(postLikeEntity.getUserEntity().getUserId());
            createdPostLikeDTO.setPostId(postLikeEntity.getPostEntity().getPostId());

            return ResponseEntity.ok(createdPostLikeDTO);
        } else {
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
        }
    }

    // 게시글 좋아요 취소
    @Transactional
    public void unlikePost(Long postId, Long likeId) {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        Optional<PostLikeEntity> optionalPostLikeEntity = postLikeRepository.findById(likeId);
        PostLikeEntity postLikeEntity = optionalPostLikeEntity.orElseThrow(() -> new RuntimeException("좋아요를 찾을 수 없습니다."));

        postLikeRepository.delete(postLikeEntity);
    }

    // 게시글 좋아요 상태 가져오기
    public boolean getPostLikeStatus(Long postId, String userId) {
        Optional<PostEntity> optionalPostEntity = postRepository.findById(postId);
        PostEntity postEntity = optionalPostEntity.orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        UserEntity userEntity = optionalUserEntity.orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        return postLikeRepository.existsByPostEntityAndUserEntity(postEntity, userEntity);
    }

    //countPostLikes 메소드 추가
    @Transactional(readOnly = true)
    public Long countPostLikes(Long postId) {
        return postLikeRepository.countLikesByPostEntityPostId(postId);
    }

}
