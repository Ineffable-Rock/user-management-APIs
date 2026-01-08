package com.backend.Backend.controller;


import com.backend.Backend.dto.CommentDTO;
import com.backend.Backend.dto.PostDTO;
import com.backend.Backend.entity.Comment;
import com.backend.Backend.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    //create a new comment
    @PostMapping
    public ResponseEntity<CommentDTO> createPost(@Valid @RequestBody CommentDTO commentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(commentDTO));
    }

    // Get all comments
    @GetMapping()
    public List<CommentDTO> getAllComments() {
        return commentService.getAllComments();
    }

    // Get comments by specific post
    // URL: http://localhost:8080/comments/posts/1
    @GetMapping("/post/{postId}")
    public List<CommentDTO> getPostsByUser(@PathVariable Long postId) {
        return commentService.getCommentByPostId(postId);
    }

}
