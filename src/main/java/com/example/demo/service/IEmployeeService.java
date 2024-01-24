package com.example.demo.service;

import com.example.demo.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IEmployeeService {
    Optional<Employee> findById(Integer id);
    Employee save(Employee employee);
    void deleteById(Integer id);
    void update(Employee employee);
    Page<Employee> findAll(Pageable pageable);
    Page<Employee> findAllByNameContains(String name, Pageable pageable);
}
