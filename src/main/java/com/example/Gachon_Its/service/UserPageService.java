package com.example.Gachon_Its.service;

import com.example.Gachon_Its.dto.CommentDTO;
import com.example.Gachon_Its.dto.PostDTO;
import com.example.Gachon_Its.dto.UserDTO;
import com.example.Gachon_Its.entity.CommentEntity;
import com.example.Gachon_Its.entity.PostEntity;
import com.example.Gachon_Its.entity.UserEntity;
import com.example.Gachon_Its.entity.UserImageEntity;
import com.example.Gachon_Its.repository.*;
import com.example.Gachon_Its.tier.UserTier;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserPageService {

    @Value("${file.userdir}")
    private String filePath;

    private final PostRepository postRepository;

    private final PostImageRepository postImageRepository;

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final UserImageRepository userImageRepository;


    public void save(UserDTO userDTO) throws IOException {
        UserEntity userEntity = new UserEntity();
        userEntity.save(userDTO);

        if (userDTO.getUserFile().isEmpty()) {

            userRepository.save(userEntity);
        } else {
            userDTO.setFileAttached(1);
            MultipartFile userFile = userDTO.getUserFile();
            String originalFilename = userFile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
            String savePath = filePath + storedFileName;
            userFile.transferTo(new File(savePath));

            UserImageEntity userImageEntity = new UserImageEntity();
            userImageEntity.save(userEntity, originalFilename, storedFileName);
            userImageRepository.save(userImageEntity);
        }

    }

    // userId로 유저가 쓴 글 다 가져오기
    @Transactional // 부모 entity가 에서 자식 entity를 호출할 때는 붙여줘야됨
    public List<PostDTO> findPostByUserId(String userId) {
        List<PostEntity> byUserId = postRepository.findByUserEntityUserId(userId);
        List<PostDTO> postDTOList = new ArrayList<>();
        for (PostEntity postEntity : byUserId) {
            PostDTO postDTO = new PostDTO();
            postDTO.save(postEntity, userId);
            postDTOList.add(postDTO);
        }
        return postDTOList;
    }

    // postId로 글 가져오기
    public PostDTO findPostByPostId(Long postId) {
        Optional<PostEntity> postEntity = postRepository.findById(postId);
        PostDTO postDTO = new PostDTO();
        postDTO.save(postEntity.get(), postEntity.get().getUserEntity().getUserId());
        return postDTO;
    }

    public Long updatePost(PostDTO postDTO, UserEntity userEntity) {
        PostEntity postEntity = new PostEntity();
        postEntity.update(postDTO, userEntity);
        postRepository.save(postEntity);
        return postDTO.getPostId();
    }

    public UserDTO findUserById(String userId) {
        Optional<UserEntity> byId = userRepository.findById(String.valueOf(userId));
        if (byId.isPresent()) {
            UserDTO userDTO = new UserDTO();
            userDTO.save(byId.get());
            return userDTO;
        } else return null;

    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public List<CommentDTO> findCommentById(String userId) {
        List<CommentEntity> commentEntityList = commentRepository.findByUserEntityUserId(userId);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity : commentEntityList) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTOList.add(commentDTO.toSaveDTO(commentEntity, userId, commentEntity.getPostEntity().getPostId()));
        }
        return commentDTOList;
    }

    public CommentDTO findCommentByCommentId(Long commentId) {
        Optional<CommentEntity> byId = commentRepository.findById(commentId);

        CommentDTO commentDTO = new CommentDTO();
        CommentDTO saveDTO = commentDTO.toSaveDTO(byId.get(), byId.get().getUserEntity().getUserId(), byId.get().getPostEntity().getPostId());
        return saveDTO;
    }

    public List<CommentDTO> findCommentByPostId(Long postId) {
        List<CommentEntity> byPostId = commentRepository.findByPostEntityPostId(postId);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity : byPostId) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.save(commentEntity);
            commentDTOList.add(commentDTO);
        }
        System.out.println("commentDTOList = " + commentDTOList);
        return commentDTOList;
    }

    public void updateComment(CommentDTO commentDTO, UserEntity userEntity, PostEntity postEntity) {
        CommentEntity commentEntity = new CommentEntity();
        CommentEntity saveEntity = commentEntity.toUpdateEntity(commentDTO, userEntity, postEntity);
        commentRepository.save(saveEntity);
    }
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }




    public UserTier tierCheck(UserDTO userById) {
        UserTier tier = null;
        int aPoint = userById.getAPoint();
        if(aPoint >=0 && aPoint <100) tier = UserTier.BASIC;
        else if (aPoint >=100 && aPoint <200) tier = UserTier.PREMIUM;
        else tier = UserTier.VIP;
        return tier;
    }

    public void updateQpoint(String userId, int pointForQ) {
        userRepository.updateQpoint(userId, pointForQ);
    }


}