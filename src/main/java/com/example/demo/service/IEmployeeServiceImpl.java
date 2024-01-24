package com.example.demo.service;

import com.example.demo.model.Employee;
import com.example.demo.repository.IEmployeeRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IEmployeeServiceImpl implements IEmployeeService{

    private final IEmployeeRepo employeeRepo;

    public IEmployeeServiceImpl(IEmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public Optional<Employee> findById(Integer id) {
        return employeeRepo.findById(id);
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepo.save(employee);
    }

    @Override
    public void deleteById(Integer id) {
        employeeRepo.deleteById(id);
    }

    @Override
    public void update(Employee employee) {
        employeeRepo.save(employee);
    }

    @Override
    public Page<Employee> findAll(Pageable pageable) {
        return employeeRepo.findAll(pageable);
    }

    @Override
    public Page<Employee> findAllByNameContains(String name, Pageable pageable) {
        return employeeRepo.findAllByNameContains(name, pageable);
    }
}
