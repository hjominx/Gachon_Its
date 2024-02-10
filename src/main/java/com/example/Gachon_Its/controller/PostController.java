package com.example.Gachon_Its.controller;

import com.example.Gachon_Its.dto.CommentDTO;
import com.example.Gachon_Its.dto.PostDTO;
import com.example.Gachon_Its.dto.UserDTO;
import com.example.Gachon_Its.entity.UserEntity;
import com.example.Gachon_Its.service.PostService;
import com.example.Gachon_Its.service.TimeTableService;
import com.example.Gachon_Its.service.UserPageService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    private final UserPageService userPageService;

    private final TimeTableService timeTableService;

    @GetMapping("/createPostForm")
    public String createForm(Model model) {
        UserDTO userById = userPageService.findUserById("wlswnsdn");
        model.addAttribute("userId", userById);
        model.addAttribute("timetableList", timeTableService.getTimeTableList());

        return "post/createPostForm";
    }

    @Transactional
    @PostMapping("/createPostForm")
    public String form(@ModelAttribute PostDTO postDTO) throws IOException {
        UserDTO userById = userPageService.findUserById(postDTO.getUserId());
        userPageService.updateQpoint(postDTO.getUserId(), postDTO.getPointForQ());
        UserEntity userEntity = new UserEntity();
        userEntity.save(userById);
        postService.save(postDTO, userEntity);

        return "post/index";
    }

    @GetMapping("/")
    public String findAll(Model model) {
        List<PostDTO> postDTOList = postService.findAll();
        model.addAttribute("postList", postDTOList);
        return "postList";
    }

    @GetMapping("/detail/{postId}")
    public String findDetail(@PathVariable Long postId, Model model) {
        PostDTO postByPostId = userPageService.findPostByPostId(postId);
        List<CommentDTO> commentByPostId = userPageService.findCommentByPostId(postId);
        model.addAttribute("post", postByPostId);
        model.addAttribute("comment", commentByPostId);
        return "post/detail";
    }


    @PostMapping("/getSearchList")
    public ResponseEntity<List<PostDTO>> getSearchList(@RequestParam String keyword) {
        List<PostDTO> searchList = postService.getSearchList(keyword);
        System.out.println("searchList = " + searchList);
        return ResponseEntity.ok(searchList);
    }

    @GetMapping("/category/{subjectId}")
    public ResponseEntity<?> getCategoryPost(@PathVariable String subjectId) {
        if (subjectId == null) return ResponseEntity.badRequest().build();
        List<PostDTO> postListBelongCategory = postService.getPostListBelongCategory(subjectId);
        System.out.println(postListBelongCategory);
        return ResponseEntity.ok(postListBelongCategory);
    }
}
