package com.example.Gachon_Its.service;

import com.example.Gachon_Its.dto.PostDTO;
import com.example.Gachon_Its.entity.PostEntity;
import com.example.Gachon_Its.entity.PostImageEntity;
import com.example.Gachon_Its.entity.UserEntity;
import com.example.Gachon_Its.repository.PostImageRepository;
import com.example.Gachon_Its.repository.PostRepository;
import lombok.RequiredArgsConstructor;
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
public class PostService {

    @Value("${file.postdir}")
    private String filePath;

    private final PostRepository postRepository;

    private final PostImageRepository postImageRepository;

    public void save(PostDTO postDTO, UserEntity userEntity) throws IOException {

        // 파일 첨부 여부에 따라 로직 분리
        if (postDTO.getPostFile().isEmpty()) {
            // 첨부 파일 없음.
            PostEntity postEntity = new PostEntity();
            postEntity.save(postDTO, userEntity);
            postRepository.save(postEntity);
        } else {
            postDTO.setFileAttached(1);
            MultipartFile postFile = postDTO.getPostFile();
            String originalFilename = postFile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
            System.out.println("storedFileName = " + storedFileName);
            String savePath = filePath + storedFileName;
            postFile.transferTo(new File(savePath));

            PostEntity postEntity = new PostEntity();
            postEntity.save(postDTO, userEntity);
            Long postId = postRepository.save(postEntity).getPostId();
            System.out.println("postId = " + postId);
            PostEntity post = postRepository.findById(postId).get();
            PostImageEntity postImageEntity = new PostImageEntity();
            postImageEntity.save(post, originalFilename, storedFileName);
            postImageRepository.save(postImageEntity);
        }
    }

    public List<PostDTO> findAll() {
        List<PostEntity> postEntityList = postRepository.findAll();
        List<PostDTO> postDTOList = new ArrayList<>();
        for(PostEntity postEntity: postEntityList) {
            PostDTO postDTO = new PostDTO();
            postDTO.save(postEntity, postEntity.getUserEntity().getUserId());
            postDTOList.add(postDTO);
        }
        return postDTOList;
    }


    public List<PostDTO> getSearchList(String keyword) {
        List<PostEntity> postEntities = postRepository.searchByTitleOrContents(keyword);
        List<PostDTO> postDTOList = new ArrayList<>();
        for (PostEntity postEntity : postEntities) {
            PostDTO postDTO = new PostDTO();
            postDTO.save(postEntity, postEntity.getUserEntity().getUserId());
            postDTOList.add(postDTO);
        }
        return postDTOList;
    }

    public List<PostDTO> getPostListBelongCategory(String subjectId){
        List<PostEntity> entityList = postRepository.findAllByCategory(subjectId);
        List<PostDTO> dtoList = new ArrayList<>();
        entityList.forEach((entity)->{
            PostDTO postDTO = new PostDTO();
            postDTO.save(entity, entity.getUserEntity().getUserId());
            dtoList.add(postDTO);
        });
        return dtoList;
    }
}
