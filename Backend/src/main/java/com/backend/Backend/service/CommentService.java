package com.backend.Backend.service;

import com.backend.Backend.dto.CommentDTO;
import com.backend.Backend.dto.PostDTO;
import com.backend.Backend.entity.Comment;
import com.backend.Backend.entity.Post;
import com.backend.Backend.repository.CommentRepository;
import com.backend.Backend.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    public CommentDTO createComment(CommentDTO commentDTO){
        Post post = postRepository.findById(commentDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + commentDTO.getPostId() ));

        Comment comment = new Comment();
        comment.setText(commentDTO.getText());
        comment.setPost(post);

        Comment saved = commentRepository.save(comment);

        return toDTO(saved);
    }

    public List<CommentDTO> getAllComments() {
        return commentRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // 3. Get Posts by User ID
    public List<CommentDTO> getCommentByPostId(Long userId) {
        return commentRepository.findByPostId(userId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // --- Helper Mapper ---
    private CommentDTO toDTO(Comment comment) {
        return new CommentDTO(
                comment.getText() ,
                comment.getPost().getId()// We grab the ID from the associated User entity
        );
    }
}
