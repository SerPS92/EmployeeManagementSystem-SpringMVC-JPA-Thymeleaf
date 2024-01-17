package com.example.demo.repository;

import com.example.demo.model.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAbsenceRepo extends JpaRepository<Absence, Integer> {
}
