package com.backend.Backend.controller;

import com.backend.Backend.dto.PostDTO;
import com.backend.Backend.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // Create a new post
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(postDTO));
    }

    // Get all posts
    @GetMapping
    public List<PostDTO> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        return postService.getAllPosts(page, size, sort);
    }

    // Get posts by specific user
    // URL: http://localhost:8080/posts/user/1
    @GetMapping("/user/{userId}")
    public List<PostDTO> getPostsByUser(@PathVariable Long userId) {
        return postService.getPostsByUserId(userId);
    }
}