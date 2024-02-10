package com.example.Gachon_Its.controller;

import ch.qos.logback.core.model.Model;
import com.example.Gachon_Its.dto.CommentDTO;
import com.example.Gachon_Its.dto.CommentLikeDTO;
import com.example.Gachon_Its.dto.UserDTO;
import com.example.Gachon_Its.service.CommentService;
import com.example.Gachon_Its.service.LikeService;
import com.example.Gachon_Its.service.UserPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final LikeService likeService;
    private final CommentService commentService;
    private final UserPageService userPageService;


//    @GetMapping("/createComment")
//    public String createComment(Model model) {
//        UserDTO userById = userPageService.findUserById("wlswnsdn");
//        userPageService.findByPostId()
//        model.addAttribute("comment", userById);
//        return "createComment";
//    }
//
//    {
//        return "createComment";
//    }
    // 댓글 생성
    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO) {
        CommentDTO createdComment = commentService.createComment(commentDTO);
        return ResponseEntity.ok(createdComment);
    }

    // 댓글 좋아요
    @PostMapping("/{commentId}/like")
    public ResponseEntity<CommentLikeDTO> likeComment(@PathVariable Long commentId, @RequestBody CommentLikeDTO commentLikeDTO) {
        ResponseEntity<CommentLikeDTO> likedComment = likeService.likeComment(commentId, commentLikeDTO);
        return likedComment;
    }
    @GetMapping("/{commentId}/like")
    public ResponseEntity<Boolean> getLikeComment(@PathVariable Long commentId, @RequestParam String userId) {
        boolean likeStatus = likeService.getLikeStatus(commentId, userId);
        return ResponseEntity.ok(likeStatus);
    }
    // 특정 댓글 조회
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long commentId) {
        CommentDTO comment = commentService.getCommentById(commentId);
        return ResponseEntity.ok(comment);
    }

    // 특정 댓글 업데이트
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long commentId, @RequestBody CommentDTO commentDTO) {
        CommentDTO updatedComment = commentService.updateComment(commentId, commentDTO);
        return ResponseEntity.ok(updatedComment);
    }

    // 특정 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

// 댓글 좋아요 취소 및 댓글 삭제
    @DeleteMapping("/{commentId}/like/{likeId}")
    public ResponseEntity<Void> unlikeComment(@PathVariable Long commentId, @PathVariable Long likeId) {
        likeService.unlikeComment(commentId, likeId);
        return ResponseEntity.noContent().build();
    }
//댓글 수 카운트
    @GetMapping("/post/{postId}/count")
    public ResponseEntity<Long> countComments(@PathVariable Long postId) {
        Long commentCount = commentService.countComments(postId);
        return ResponseEntity.ok(commentCount);
    }

}