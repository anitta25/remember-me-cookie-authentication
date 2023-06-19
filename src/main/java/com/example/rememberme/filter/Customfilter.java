package com.example.rememberme.filter;

import com.example.rememberme.entitiy.User;
import com.example.rememberme.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jfr.SettingControl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
public class Customfilter  extends OncePerRequestFilter {
    @Autowired
    UserRepository userRepository;
    int flag=0;
    Cookie requiredCookie;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("inside dofilterinternal");
        Cookie []cookies = request.getCookies();

        String  requiredCookievalue;
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                log.info("inside for loop ");

                if (cookie.getName().equals("remembermetoken")) {
                    flag = 1;
                    log.info("there is a remember me token");
                    requiredCookievalue = cookie.getValue();

                    User user = userRepository.findByRemembermetoken(requiredCookievalue)
                            .orElseThrow(() -> new UsernameNotFoundException("user not found"));

                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), null);
                    Authentication authentication = token;
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    break;
                }

            }}
            if (flag == 0)
                log.info("no rememberme cookie");

        filterChain.doFilter(request,response);
    }
}
