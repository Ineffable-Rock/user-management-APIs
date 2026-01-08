package com.backend.Backend.service;

import com.backend.Backend.entity.User;
import com.backend.Backend.dto.UserDTO;
import com.backend.Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // --- PUBLIC METHODS (Now using DTOs) ---

    // 2. Return List<UserDTO> instead of List<User>
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        // Convert the list of Entities to a list of DTOs
        return users.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // 3. Accept UserDTO and Return UserDTO
    public UserDTO saveUser(UserDTO userDTO) {
        // Convert DTO to Entity to save it
        User userEntity = toEntity(userDTO);
        User savedUser = userRepository.save(userEntity);
        // Convert back to DTO to return it
        return toDTO(savedUser);
    }

    // We can keep these as Entity for now, or convert them too.
    // Let's leave them for a moment to minimize errors, but ideally they should be DTOs too.
    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, User userDetails) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(userDetails.getUsername());
            existingUser.setEmail(userDetails.getEmail());
            return userRepository.save(existingUser);
        }
        return null;
    }

    private User toEntity(UserDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());

        // ENCRYPT the password before saving to DB
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Set Role (Default to "USER" if not provided)
        user.setRole(dto.getRole() == null ? "USER" : dto.getRole());
        return user;
    }

    private UserDTO toDTO(User user) {
        return new UserDTO(
                user.getUsername(),
                user.getEmail()
                // IMPORTANT: Do NOT put the password here.
                // We never send the password back to the frontend.
        );
    }
}