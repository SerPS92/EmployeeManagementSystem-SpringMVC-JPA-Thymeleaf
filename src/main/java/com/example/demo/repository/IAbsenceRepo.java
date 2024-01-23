package com.example.demo.repository;

import com.example.demo.model.Absence;
import com.example.demo.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAbsenceRepo extends JpaRepository<Absence, Integer> {
    Page<Absence> findByEmployee(Employee employee, Pageable pageable);
}
