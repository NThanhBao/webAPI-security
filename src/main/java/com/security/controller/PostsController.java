package com.security.controller;

import com.security.model.DTO.PostsDTO;
import com.security.service.serviceImpl.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PostsController {

    private final PostService postsService;


    @Autowired
    public PostsController(PostService productService) {
        this.postsService = productService;
    }

    @PostMapping("/posts")
    public ResponseEntity<String> savePost(@RequestBody PostsDTO postDTO) {
        String message = postsService.savePost(postDTO);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostsDTO>> getAllPosts() {
        List<PostsDTO> posts = postsService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostsDTO> getPostById(@PathVariable Long id) {
        return postsService.getPostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/posts/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostsDTO> updatePost(@PathVariable Long id, @RequestBody PostsDTO updatedPostDTO) {
        PostsDTO updatedPost = postsService.updatePost(id, updatedPostDTO);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/posts/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postsService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully");
    }
}