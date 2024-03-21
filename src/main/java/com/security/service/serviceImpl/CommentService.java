package com.security.service.serviceImpl;

import com.security.model.DTO.CommentDTO;
import com.security.model.entity.Comment;
import com.security.model.entity.Posts;
import com.security.model.entity.UserInfo;
import com.security.repository.CommentRepo;
import com.security.repository.PostsRepository;
import com.security.repository.UserInfoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepo commentRepo;
    private final PostsRepository postRepository;
    private final UserInfoRepository userInfoRepository;


    @Autowired
    public CommentService( PostsRepository postRepository, UserInfoRepository userInfoRepository,
                          CommentRepo commentRepo) {
        this.postRepository = postRepository;
        this.userInfoRepository = userInfoRepository;
        this.commentRepo = commentRepo;
    }


    public String saveComment(CommentDTO commentDTO){
        Optional<UserInfo> userInfoOptional = userInfoRepository.findByUsername(commentDTO.getUsername());
        Optional<Posts> postOptional = postRepository.findById(commentDTO.getIdPost());
        if (userInfoOptional.isPresent() && postOptional.isPresent()) {
            UserInfo userInfo = userInfoOptional.get();
            Posts posts = postOptional.get();
            Comment post = new Comment();
            BeanUtils.copyProperties(commentDTO, post);
            post.setUser(userInfo);
            post.setPost(posts);

            commentRepo.save(post);
            return "comment added successfully.";

        } else {
            throw new RuntimeException("UserInfo not found for username: ");
        }
    }
}
