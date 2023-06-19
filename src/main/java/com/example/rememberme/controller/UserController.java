package com.example.rememberme.controller;

import com.example.rememberme.DTO.LoginRequestDTO;
import com.example.rememberme.DTO.SignUpRequestDTO;
import com.example.rememberme.service.RemembermeService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class UserController {
    @Autowired
    RemembermeService rememberMeService;
    @GetMapping("/hello")
    public String hello()
    {
        return "hello";
    }
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpRequestDTO signUpRequestDTO)
    {  log.info("inside signup");
       return  rememberMeService.signup(signUpRequestDTO);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO, @RequestParam (defaultValue = "false")  boolean remember, HttpServletResponse httpServletResponse)
    {
       return rememberMeService.login(loginRequestDTO,remember,httpServletResponse);
    }
    @GetMapping("/privatepage")
            public String privatepage()
    {
        log.info("inside private page");

        return "private page";
    }
 }
