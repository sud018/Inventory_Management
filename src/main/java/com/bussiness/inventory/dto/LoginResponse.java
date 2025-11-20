package com.bussiness.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private String type = "Bearer";
    private Long userId;
    private String username;
    private String role;
    private String message;

    public LoginResponse(String token, Long userId, String username, String role){
        this.token = token;
        this.type = "Bearer";
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.message = "Login successful";
    }



}
