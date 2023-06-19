package com.example.rememberme.service;

import com.example.rememberme.entitiy.User;
import com.example.rememberme.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository repository;
    User user;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(!repository.findByUsername(username).isPresent())
        {
            throw new UsernameNotFoundException("username not found");
        }
        else
             user= repository.findByUsername(username).get();
            return  new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),new ArrayList<>());
    }}
