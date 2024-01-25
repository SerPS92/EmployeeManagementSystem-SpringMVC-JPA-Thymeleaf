package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/show")
    public String show(@RequestParam(name = "page", defaultValue = "0")int page,
                       @RequestParam(name = "size", defaultValue = "8")int size,
                       Model model){
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userService.findAll(pageable);
        model.addAttribute("users", users);
        return "user/users";
    }

    @GetMapping("/create")
    public String create(){
        return "user/create";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id")Integer id,
                       Model model){
        User user = userService.findById(id).orElse(null);
        model.addAttribute("user", user);
        return "user/edit";
    }

    @PostMapping("/save")
    public String save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return "redirect:/users/show";
    }

    @PostMapping("/update")
    public String update(User user){
        Optional<User> optionalUser = userService.findById(Math.toIntExact(user.getId()));

        if(optionalUser.isPresent() && optionalUser.get().getPassword().equals(user.getPassword())){
            user.setPassword(optionalUser.get().getPassword());
        } else if(optionalUser.isPresent()){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userService.update(user);
        return "redirect:/users/show";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id")Integer id){
        userService.deleteById(id);
        return "redirect:/users/show";
    }

}
