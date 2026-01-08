package com.backend.Backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CommentDTO {
    @NotBlank(message = "comment can not be blank")
    private String text;

    @NotNull(message = "Post id can not be null")
    private Long postId;

    public CommentDTO(){};

    public CommentDTO(String text, Long postId){
        this.text = text;
        this.postId = postId;
    }

    public String getText() {
        return text;
    }

    public void setText( String text) {
        this.text = text;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
