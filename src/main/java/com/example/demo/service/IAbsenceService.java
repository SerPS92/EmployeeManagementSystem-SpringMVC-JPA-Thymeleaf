package com.example.demo.service;

import com.example.demo.model.Absence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IAbsenceService {

    Optional<Absence> findById(Integer id);
    Absence save(Absence absence);
    void deleteById(Integer id);
    void update(Absence absence);
    Page<Absence> findAll(Pageable pageable);
}
