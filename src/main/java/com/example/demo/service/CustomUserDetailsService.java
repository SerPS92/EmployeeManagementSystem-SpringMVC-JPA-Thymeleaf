package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.IUserRepo;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private final IUserService userService;
    private final HttpSession session;

    public CustomUserDetailsService(IUserService userService, HttpSession session) {
        this.userService = userService;
        this.session = session;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadByUsername");
        log.info("phone: {}", username);
        Optional<User> optionalUser = userService.findByPhone(username);
        if(optionalUser.isPresent()){
            log.info("user found");
            User user = optionalUser.get();
            session.setAttribute("idUser", user.getId());
            log.info("idSesion:{}", session.getAttribute("idUser"));
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getPhone())
                    .password(user.getPassword())
                    .roles(user.getType())
                    .build();
        } else{
            log.error("user not found");
            throw new UsernameNotFoundException("User not found");
        }
    }
}
