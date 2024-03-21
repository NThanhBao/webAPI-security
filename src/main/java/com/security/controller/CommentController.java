package com.security.controller;

import com.security.model.DTO.CommentDTO;
import com.security.service.serviceImpl.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping("/comment")
    public ResponseEntity<String> savePost(@RequestBody CommentDTO commentDTO) {
        String message = commentService.saveComment(commentDTO);
        return ResponseEntity.ok(message);
    }
}
