package com.backend.Backend.service;

import com.backend.Backend.dto.UserDTO;
import com.backend.Backend.entity.User;
import com.backend.Backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // 1. Setup Mockito
class UserServiceTest {

    @Mock // 2. Create a Fake Repository
    private UserRepository userRepository;

    @InjectMocks // 3. Inject the Fake into the Real Service
    private UserService userService;

    @Mock // <--- ADD THIS
    private PasswordEncoder passwordEncoder;

    @Test
    void testSaveUser_Success() {
        // --- ARRANGE ---
        UserDTO inputDTO = new UserDTO("krish", "krish@test.com", "123", "USER"); // Add password/role to constructor if needed, or use setters:
        // inputDTO.setPassword("123");
        // inputDTO.setRole("USER");

        User savedEntity = new User();
        savedEntity.setId(1L);
        savedEntity.setUsername("krish");
        savedEntity.setEmail("krish@test.com");
        savedEntity.setPassword("encoded_123"); // Simulating encrypted password
        savedEntity.setRole("USER");

        // 1. Mock the Encoder: "When asked to encode, just return 'encoded_123'"
        when(passwordEncoder.encode(any())).thenReturn("encoded_123"); // <--- ADD THIS

        // 2. Mock the Repo
        when(userRepository.save(any(User.class))).thenReturn(savedEntity);

        // --- ACT ---
        UserDTO result = userService.saveUser(inputDTO);

        // --- ASSERT ---
        assertNotNull(result);
        assertEquals("krish", result.getUsername());
    }
}