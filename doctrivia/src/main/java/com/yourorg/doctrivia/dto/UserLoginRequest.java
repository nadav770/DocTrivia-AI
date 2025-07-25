package com.yourorg.doctrivia.dto;
import lombok.Data;

@Data
public class UserLoginRequest {
    private String username; // או אפשר לשנות ל־email בלבד
    private String email;    // אפשר להשאיר את שניהם, או לעבור ל־email בלבד
    private String password;
}
