package com.example.Gachon_Its.controller;

import com.example.Gachon_Its.dto.CommentLikeDTO;
import com.example.Gachon_Its.dto.PostLikeDTO;
import com.example.Gachon_Its.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/like")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/comment/{commentId}")
    public ResponseEntity<CommentLikeDTO> likeComment(@PathVariable Long commentId, @RequestBody CommentLikeDTO commentLikeDTO) {
        try {
            return likeService.likeComment(commentId, commentLikeDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/comment/{commentId}/like/{likeId}")
    public ResponseEntity<Void> unlikeComment(@PathVariable Long commentId, @PathVariable Long likeId) {
        try {
            likeService.unlikeComment(commentId, likeId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/comment/{commentId}/status")
    public ResponseEntity<Boolean> getCommentLikeStatus(@PathVariable Long commentId, @RequestParam String userId) {
        boolean likeStatus = likeService.getPostLikeStatus(commentId, userId);
        return ResponseEntity.ok(likeStatus);
    }


    @PostMapping("/post/{postId}")
    public ResponseEntity<Object> likePost(@PathVariable Long postId, @RequestBody PostLikeDTO postLikeDTO) {
        try {
            return likeService.likePost(postId, postLikeDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/post/{postId}/like/{likeId}")
    public ResponseEntity<Void> unlikePost(@PathVariable Long postId, @PathVariable Long likeId) {
        try {
            likeService.unlikePost(postId, likeId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/post/{postId}/status")
    public ResponseEntity<Boolean> getPostLikeStatus(@PathVariable Long postId, @RequestParam String userId) {
        boolean likeStatus = likeService.getPostLikeStatus(postId, userId);
        return ResponseEntity.ok(likeStatus);
    }

    //좋아요 수 카운트
    @GetMapping("/post/{postId}/count")
    public ResponseEntity<Long> countPostLikes(@PathVariable Long postId) {
        Long likeCount = likeService.countPostLikes(postId);
        return ResponseEntity.ok(likeCount);
    }

}
