package com.example.rememberme.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data@AllArgsConstructor
public class LoginRequestDTO {
    String username,password;
}
