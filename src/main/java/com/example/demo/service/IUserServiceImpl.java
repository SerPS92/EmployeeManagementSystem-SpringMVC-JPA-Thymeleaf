package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.IUserRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IUserServiceImpl implements IUserService{

    private final IUserRepo userRepo;

    public IUserServiceImpl(IUserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> findByPhone(String phone) {
        return userRepo.findByPhone(phone);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepo.save(user);
    }

    @Override
    public void deleteById(Integer id) {
        userRepo.deleteById(id);
    }

    @Override
    public void update(User user) {
        userRepo.save(user);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepo.findAll(pageable);
    }
}
