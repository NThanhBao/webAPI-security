package com.security.service.serviceImpl;


import com.security.model.DTO.PostsDTO;
import com.security.model.entity.Posts;
import com.security.model.entity.UserInfo;
import com.security.repository.PostsRepository;
import com.security.repository.UserInfoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostsRepository postRepository;
    private final UserInfoRepository userInfoRepository;

    @Autowired
    public PostService(PostsRepository postRepository, UserInfoRepository userInfoRepository) {
        this.postRepository = postRepository;
        this.userInfoRepository = userInfoRepository;
    }

    public String savePost(PostsDTO postDTO) {
        Optional<UserInfo> userInfoOptional = userInfoRepository.findByUsername(postDTO.getUsername());

        if (userInfoOptional.isPresent()) {
            UserInfo userInfo = userInfoOptional.get();
            Posts post = new Posts();
            BeanUtils.copyProperties(postDTO, post);
            post.setUserInfo(userInfo);

            postRepository.save(post);
            return "Post added successfully.";

        } else {
            throw new RuntimeException("UserInfo not found for username: " + postDTO.getUsername());
        }
    }
    public List<PostsDTO> getAllPosts() {
        List<Posts> posts = postRepository.findAll();
        return posts.stream()
                .map(post -> {
                    PostsDTO postDTO = new PostsDTO();
                    BeanUtils.copyProperties(post, postDTO);
                    return postDTO;
                })
                .collect(Collectors.toList());
    }
    public Optional<PostsDTO> getPostById(Long id) {
        Optional<Posts> postOptional = postRepository.findById(id);
        return postOptional.map(post -> {
            PostsDTO postDTO = new PostsDTO();
            BeanUtils.copyProperties(post, postDTO);
            return Optional.of(postDTO);
        }).orElse(Optional.empty());
    }

    public PostsDTO updatePost(Long id, PostsDTO updatedPostDTO) {
        Optional<Posts> existingPostOptional = postRepository.findById(id);
        if (existingPostOptional.isPresent()) {
            Posts existingPost = existingPostOptional.get();
            BeanUtils.copyProperties(updatedPostDTO, existingPost);
            Posts updatedPost = postRepository.save(existingPost);
            PostsDTO updatedPostResponse = new PostsDTO();
            BeanUtils.copyProperties(updatedPost, updatedPostResponse);
            return updatedPostResponse;
        } else {
            throw new RuntimeException("Post not found");
        }
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
