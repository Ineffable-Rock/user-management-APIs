package com.backend.Backend.service;

import com.backend.Backend.dto.PostDTO;
import com.backend.Backend.entity.Post;
import com.backend.Backend.entity.User;
import com.backend.Backend.repository.PostRepository;
import com.backend.Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public PostDTO createPost(PostDTO postDTO){
        // Step A: Find the User who is writing this post
        User user  = userRepository.findById(postDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID:" + postDTO.getUserId()));

        // Step B: Create the Entity and link the User
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setUser(user); // <--- THIS IS THE KEY! Linking the Foreign Key

        Post savedPost = postRepository.save(post);

        // return DTO
        return toDTO(savedPost);
    }

    public List<PostDTO> getAllPosts(int pageNo, int pageSize, String sortBy) {

        // 1. Create a "Pageable" object
        // This tells Spring: "Give me page 'pageNo', with 'pageSize' items, sorted by 'sortBy'"
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());

        // 2. Fetch the Page from DB
        Page<Post> postsPage = postRepository.findAll(pageable);

        // 3. Get the list of content from the Page object
        List<Post> listOfPosts = postsPage.getContent();

        // 4. Convert to DTOs as usual
        return listOfPosts.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // 3. Get Posts by User ID
    public List<PostDTO> getPostsByUserId(Long userId) {
        return postRepository.findByUserId(userId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // --- Helper Mapper ---
    private PostDTO toDTO(Post post) {
        return new PostDTO(
                post.getTitle(),
                post.getDescription(),
                post.getUser().getId() // We grab the ID from the associated User entity
        );
    }
}
