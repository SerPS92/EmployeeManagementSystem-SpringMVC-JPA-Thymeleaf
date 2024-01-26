package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("")
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(HomeController.class);
    private final IUserService userService;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public HomeController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {

        Pageable pageable = PageRequest.of(0, 8);
        Page<User> users = userService.findAll(pageable);

        if (users.isEmpty()) {
            User user = new User();
            user.setId(1L);
            user.setName("Admin");
            user.setLastName("Admin");
            user.setEmail("1");
            user.setPhone("1");
            user.setType("admin");
            user.setPassword(passwordEncoder.encode("1"));
            log.info("User create");
            log.info("Username: {}", user.getPhone());
            log.info("Password: {}", "1");
            userService.save(user);
        }

        return "login";
    }

    @PostMapping("/auth")
    public String auth(HttpSession session) {
        Long idUser = (Long) session.getAttribute("idUser");
        int id = idUser.intValue();
        Optional<User> optionalUser = userService.findById(id);
        if (optionalUser.isPresent()) {
            log.info("Auth");
            User user = optionalUser.get();
            session.setAttribute("name", user.getName());
            session.setAttribute("type", user.getType());
            log.info("idUser: {}", user.getId());
            log.info("Name: {}", user.getName());
            log.info("Type: {}", user.getType());
        }
        return "index";
    }

    @GetMapping("/close")
    public String close(HttpSession session) {
        log.info("close");
        session.removeAttribute("idUser");
        session.removeAttribute("name");
        session.removeAttribute("type");

        return "redirect:/login";
    }
}
