package com.security.controller;

import com.security.model.DTO.CommentDTO;
import com.security.service.serviceImpl.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping("/comment")
    public ResponseEntity<String> saveComment(@RequestBody CommentDTO commentDTO) {
        String message = commentService.saveComment(commentDTO);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/comment/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok("Post deleted successfully");
    }
}
