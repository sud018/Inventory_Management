package com.bussiness.inventory.service;

import java.time.LocalDateTime;
import java.util.List;

import com.bussiness.inventory.dto.LoginRequest;
import com.bussiness.inventory.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bussiness.inventory.annotation.Encrypt;
import com.bussiness.inventory.dto.SignupRequest;
import com.bussiness.inventory.dto.SignupResponse;
import com.bussiness.inventory.dto.LoginResponse;
import com.bussiness.inventory.model.User;
import com.bussiness.inventory.repository.UserRepository;
import com.bussiness.inventory.util.EncryptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import jakarta.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EncryptionUtil encryptionUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Transactional
    @Encrypt(field = "password")
    public SignupResponse signup(SignupRequest request){
           validateRequest(request);

           String emailHash = encryptionUtil.hashEmailWithMD5(request.getEmail());

           if(userRepository.existsByUsername(request.getUsername())){
            throw new RuntimeException("username already exists");
           }
            
           if(userRepository.existsByEmail(emailHash)){
            throw new RuntimeException("email already exists");
           }
           User user = new User();
           user.setFirstname(request.getFirstname());
           user.setLastname(request.getLastname());
           user.setUsername(request.getUsername());
           user.setEmail(emailHash);
           user.setRole(request.getRole()!=null?request.getRole():"USER");
           user.setPassword(passwordEncoder.encode(request.getPassword()));
           user.setCreatedAt(LocalDateTime.now());

           User saveduser = userRepository.save(user);
           System.out.println("user saved successfully "+ saveduser.getUsername());
           return new SignupResponse(
            saveduser.getId(),
            saveduser.getFirstname(),
            saveduser.getLastname(),
            saveduser.getUsername(),
            request.getEmail(),
            saveduser.getRole(),
            "User registered successfully"
           );
    }

    private void validateRequest(SignupRequest request){
        if(request.getFirstname()==null || request.getFirstname().trim().isEmpty()){
            throw new IllegalArgumentException("Firstname is required");
        }
        if(request.getLastname()==null || request.getLastname().trim().isEmpty()){
            throw new IllegalArgumentException("Lastname is required");
        }
        if(request.getUsername()==null || request.getUsername().trim().isEmpty()){
            throw new IllegalArgumentException("username is required");
        }
        if(request.getUsername().length()<3){
            throw new IllegalArgumentException("username should be minimum 3 characters long");
        }
         if(request.getEmail()==null || request.getEmail().trim().isEmpty()){
            throw new IllegalArgumentException("Email is required");
        }
         if(!request.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")){
            throw new IllegalArgumentException("Invalid Email");
         }
        if(request.getPassword()==null || request.getPassword().length()<6){
            throw new IllegalArgumentException("password should be atleast 6 characters long");
        }
    }
    
    public List<User> getAllUsers(){
        List<User> users = userRepository.findAllUsers();
        return users;
    }
    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("user not found"));
    }
    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("email not found"));
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword){
           return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Transactional
    public void updateLastLogin(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        user.setLastlogin(LocalDateTime.now());
        userRepository.save(user);
    }

// --------------   Login ---------------------

    public LoginResponse login(LoginRequest request){
        if(request.getUsername() ==null || request.getUsername().trim().isEmpty()){
            throw new IllegalArgumentException("username is required");
        }
        if(request.getPassword() == null || request.getPassword().trim().isEmpty()){
            throw new IllegalArgumentException("password is required");
        }
       User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new RuntimeException("user not found"));
       if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
           throw new RuntimeException("Invalid password");
        }
       user.setLastlogin(LocalDateTime.now());
       userRepository.save(user);
       String token = jwtUtil.generateToken(user.getUsername(), user.getRole(), user.getId());

        logger.info("✅ User logged in successfully: {}", user.getUsername());
        logger.debug("🔑 Generated token : {}...", token.substring(0, 20));

        return new LoginResponse(
                token,
                user.getId(),
                user.getUsername(),
                user.getRole()
        );

    }
}
