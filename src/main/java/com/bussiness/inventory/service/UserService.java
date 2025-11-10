package com.bussiness.inventory.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bussiness.inventory.annotation.Encrypt;
import com.bussiness.inventory.dto.SignupRequest;
import com.bussiness.inventory.dto.SignupResponse;
import com.bussiness.inventory.model.User;
import com.bussiness.inventory.repository.UserRepository;
import com.bussiness.inventory.util.EncryptionUtil;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EncryptionUtil encryptionUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
}
