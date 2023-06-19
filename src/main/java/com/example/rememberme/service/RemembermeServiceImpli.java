package com.example.rememberme.service;

import com.example.rememberme.DTO.LoginRequestDTO;
import com.example.rememberme.DTO.SignUpRequestDTO;
import com.example.rememberme.entitiy.User;
import com.example.rememberme.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class RemembermeServiceImpli implements RemembermeService{
    @Autowired
    UserRepository repo;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<String> signup(SignUpRequestDTO signUpRequestDTO) {
        if(repo.findByUsername(signUpRequestDTO.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("username already exists");
        }
        else {
            User user = new User();
            user.setUsername(signUpRequestDTO.getUsername());
            user.setPassword(passwordEncoder.encode(signUpRequestDTO.getPassword()));
            repo.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("account created");
        }
    }

    @Override
    public ResponseEntity<String> login(LoginRequestDTO loginRequestDTO, boolean remember, HttpServletResponse httpServletResponse) {
        String username=loginRequestDTO.getUsername();
        String password=loginRequestDTO.getPassword();
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(username,password,null);
        try {
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);


            if(remember)
            {  log.info("value of remember  parameter is  true");
                UUID uuid =UUID.randomUUID();
                jakarta.servlet.http.Cookie cookie =new Cookie("remembermetoken",uuid.toString());
                //10 days expiration
                cookie.setMaxAge(10*24*60*60);
//dont forget to pass remember me parameter in postman
                httpServletResponse.addCookie(cookie);

               User user= repo.findByUsername(username).get();
               user.setRemembermetoken(uuid.toString());
               repo.save(user);
            }
            return ResponseEntity.status(HttpStatus.OK).body("successful login");
        }
        catch (AuthenticationException exception)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("login fail");
        }

    }
}
