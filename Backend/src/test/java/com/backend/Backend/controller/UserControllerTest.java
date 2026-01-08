package com.backend.Backend.controller;

import com.backend.Backend.dto.UserDTO;
import com.backend.Backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; // Import

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean // <--- UPDATED ANNOTATION
    private UserService userService;

    @Test
    void testGetAllUsers() throws Exception {

        // Prepare some fake data
        UserDTO user1 = new UserDTO("Alice", "alice@test.com");
        UserDTO user2 = new UserDTO("Bob", "bob@test.com");
        List<UserDTO> fakeUsers = Arrays.asList(user1, user2);

        // Tell the Mock Service: "When controller asks for users, return this list"
        given(userService.getAllUsers()).willReturn(fakeUsers);

        // --- ACT & ASSERT ---
        // Send a fake GET request to /users
        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Expect 200 OK
                .andExpect(jsonPath("$[0].username").value("Alice")) // Check JSON data
                .andExpect(jsonPath("$[1].username").value("Bob"));
    }
}