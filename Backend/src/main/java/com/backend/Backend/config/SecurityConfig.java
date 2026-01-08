package com.backend.Backend.config;

import com.backend.Backend.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


//🧠 What does this code do?
//UserDetailsService: This bean is like a VIP list.
//
//We added "krish" (password: 123).
//
//We added "admin" (password: admin).
//
//        {noop}: This tells Spring Security "Do not encrypt this password, just read it as plain text." (We will use real encryption later).
//
//SecurityFilterChain: This bean is the rulebook.
//
//        csrf.disable(): Essential for REST APIs. Without this, Postman POST requests would get blocked.
//
//anyRequest().authenticated(): Means "Lock every single door."
//
//httpBasic(): Tells the browser/Postman to send the username/password in the Header.

@Configuration
//@Configuration: This tells Spring: "Treat this class like a Factory."
//When Spring starts, it looks inside this class for methods annotated with @Bean. It runs them to create "objects" (Beans) that the rest of the app can use.
@EnableWebSecurity
//@EnableWebSecurity: This is the "On Switch."
//Without this, Spring Security is technically on the classpath but sleeping. This annotation wakes it up and says, "I am taking control of all web requests."
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;
    // 1. Define the Users (In-Memory for now)
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder()
//                .username("krish")      // Set your username
//                .password(passwordEncoder().encode("123"))  // Set your password. "{noop}" means "No Operation" (No encryption yet)
//                .roles("USER")          // Assign a role
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }
//    UserDetailsService: This is a standard Interface in Spring. It has one job: Load user data. It doesn't check passwords; it just finds the user.
//
//            User.builder()...build(): This creates a simple User object.
//
//    {noop}: This stands for "No Operation."
//
//    Why? Spring Security demands password encryption by default. If you give it "123", it crashes because it expects a hashed string like $2a$10$X7....
//
//    {noop} tells Spring: "Relax, this is just a demo. Don't try to decrypt this. Just compare the plain text."
//
//    InMemoryUserDetailsManager: This is a temporary storage.
//
//    Instead of looking up users in your PostgreSQL database (which is hard to set up), this class keeps the users in RAM (Computer Memory). If you restart the app, the memory clears, and the users are reset.

    // 2. Define the Rules (Filter Chain)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF (Cross-Site Request Forgery) for Postman/APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/users").permitAll()
                        .anyRequest().authenticated() // "All requests require a password"
                )
                .httpBasic(Customizer.withDefaults()); // Use Basic Auth (What Postman uses)

        return http.build();
    }

//    SecurityFilterChain: This is the "Wall" we talked about. Every request hits this chain. If it passes all the filters in the chain, it reaches your Controller.
//    HttpSecurity http: This object is your "Builder." It lets you chain rules together using .method().method().

//    .csrf(csrf -> csrf.disable())
//    What is CSRF? Cross-Site Request Forgery. It's an attack where a bad website tricks your browser into sending a request to your bank.
//    Why disable it? CSRF protection relies on "Sessions" (Cookies). REST APIs (like ours) are usually "Stateless" (we send the password every time). CSRF protection actually breaks POST requests in Postman if you don't disable it.
//            .authorizeHttpRequests(...)
//    This starts the list of "Who can go where?"
//            .anyRequest().authenticated(): This is the strictest rule. It says: "I don't care what URL you are asking for (/users, /posts, /images). If you don't have a valid ID, you are blocked."
//            .httpBasic(...)
//    This defines how you show your ID.
//    Basic Auth is the simplest method: You put username:password inside the Request Header.
//    If we used .formLogin(), Spring would send a generated HTML Login Page (good for websites, bad for APIs).
//
//    Summary of the Flow
//    When you send a request to GET /users:
//    SecurityFilterChain stops you. "Show me your ID (HTTP Basic)."
//    You send krish:123.
//    UserDetailsService checks its clipboard (In-Memory).
//            "Do I have a 'krish'?" -> Yes.
//"Is the password '123'?" -> Yes (thanks to {noop}).
//    SecurityFilterChain checks the rules.
//            "Is this user allowed to see /users?" -> Yes (authenticated()).
//    Controller finally gets the request and returns the data.

// the dynamic way
    // 2. The Authentication Provider (The Bridge)
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

//     3. Define the Password Encoder (The Encryption Tool)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}