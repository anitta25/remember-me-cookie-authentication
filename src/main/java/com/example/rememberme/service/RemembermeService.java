package com.example.rememberme.service;

import com.example.rememberme.DTO.LoginRequestDTO;
import com.example.rememberme.DTO.SignUpRequestDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface  RemembermeService {

    ResponseEntity<String> signup(SignUpRequestDTO signUpRequestDTO);

    ResponseEntity<String> login(LoginRequestDTO loginRequestDTO, boolean remember, HttpServletResponse httpServletResponse);
}
