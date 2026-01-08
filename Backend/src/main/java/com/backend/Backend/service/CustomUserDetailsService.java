package com.backend.Backend.service;

import com.backend.Backend.entity.User; // YOUR User Entity
import com.backend.Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Find the user in the DB
        User user = userRepository.findByUsername(username);
        // Note: If you get a "Cannot resolve method" error here, we will fix it in the Repository next!

        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }

        // 2. Translate your Entity -> Spring Security User
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // Pass the encrypted password
                .roles(user.getRole())        // Pass the role
                .build();
    }
}