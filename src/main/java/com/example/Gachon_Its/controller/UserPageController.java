package com.example.Gachon_Its.controller;

import com.example.Gachon_Its.dto.CommentDTO;
import com.example.Gachon_Its.dto.PostDTO;
import com.example.Gachon_Its.dto.UserDTO;
import com.example.Gachon_Its.entity.PostEntity;
import com.example.Gachon_Its.entity.UserEntity;
import com.example.Gachon_Its.service.UserPageService;
import com.example.Gachon_Its.tier.UserTier;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/userPage")
@RequiredArgsConstructor
public class UserPageController {

    private final UserPageService userPageService;


//    내가 쓴 글들 가져오기
    @GetMapping("/post")
    public String findUserPost(@RequestParam String userId, Model model) {
        List<PostDTO> userPostDTOList = userPageService.findPostByUserId(userId);
        model.addAttribute("postList", userPostDTOList);
        return "postList";
    }


    // 내가 수정할 글 가져오기
    @GetMapping("/updatePost/{postId}")
    public String findUpdatePost(@PathVariable Long postId, Model model) {
        PostDTO byPostId = userPageService.findPostByPostId(postId);
        model.addAttribute("byPostId", byPostId);
        return "updatePost";
    }

    // 내가 수정한 글 갖고오기

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute PostDTO postDTO) {
        UserDTO userDTO = userPageService.findUserById(postDTO.getUserId());
        UserEntity userEntity = new UserEntity();
        userEntity.save(userDTO);
        userPageService.updatePost(postDTO, userEntity);
        return "redirect:/";
    }

    // 내가 쓴 글 삭제
    @GetMapping("/deletePost/{postId}")
    public String deletePost(@PathVariable Long postId) {
        userPageService.deletePost(postId);
        return "redirect:/";
    }

    // 내 회원 정보 가져오기
    @GetMapping("/info")
    public String findUserInfo(@RequestParam String userId, Model model) {
        UserDTO userById = userPageService.findUserById(userId);
        UserTier userTier = userPageService.tierCheck(userById);
        System.out.println("userTier = " + userTier);
        model.addAttribute("user", userById);
        return "userInfo";
    }



    // 회원 정보 수정할 거 가져오기
    @GetMapping("/updateInfo/{userId}")
    public String findUpdateUser(@PathVariable String userId, Model model) {
        UserDTO userById = userPageService.findUserById(userId);
        model.addAttribute("userInfo", userById);
        return "updateUser";
    }


    // 회원 정보 수정한 거 가져오기
    @PostMapping("/updateInfo")
    public String updateInfo(@ModelAttribute UserDTO userDTO) throws IOException {
        userPageService.save(userDTO);
        return "redirect:/";
    }

    // 내가 쓴 댓글 가져오기
    @GetMapping("/comment")
    @ResponseBody
    public List<CommentDTO> findUserComment(@RequestParam String userId) {
        List<CommentDTO> commentById = userPageService.findCommentById(userId);
        return commentById;
    }
//
    // 내가 수정할 댓글 가져오기
    @GetMapping("/updateComment/{commentId}")
    public String findUpdateComment(@PathVariable Long commentId, Model model) {
        CommentDTO byCommentId = userPageService.findCommentByCommentId(commentId);
        model.addAttribute("byCommentId", byCommentId);
        return "updateComment";
    }

    @PostMapping("/updateComment")
    public String updateComment(@ModelAttribute CommentDTO commentDTO) {
        // 사용자 정보 가져오기
        UserDTO userInfo = userPageService.findUserById(commentDTO.getUserId());
        UserEntity userEntity = new UserEntity();
        userEntity.save(userInfo);

        // 게시물 정보 가져오기
        PostDTO postByPostId = userPageService.findPostByPostId(commentDTO.getPostId());
        PostEntity postEntity = new PostEntity();
        postEntity.update(postByPostId, userEntity);

        // 댓글 엔터티 업데이트
        userPageService.updateComment(commentDTO, userEntity, postEntity);

        // 원하는 리다이렉션 주소로 변경
        return "redirect:/userPage/comment?userId=" + commentDTO.getUserId();
    }


    // 내가 쓴 댓글 삭제
    @GetMapping("/deleteComment/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        userPageService.deleteComment(commentId);
    }


}
