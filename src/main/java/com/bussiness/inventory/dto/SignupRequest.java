package com.bussiness.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private String role;
}