package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<User> findById(Integer id);
    Optional<User> findByPhone(String phone);
    Optional<User> findByEmail(String email);
    User save(User user);
    void deleteById(Integer id);
    void update(User user);
    Page<User> findAll(Pageable pageable);

}
