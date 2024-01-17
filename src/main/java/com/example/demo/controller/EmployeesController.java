package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.service.IEmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/employees")
public class EmployeesController {

    private final Logger log = LoggerFactory.getLogger(EmployeesController.class);

    private final IEmployeeService employeeService;

    public EmployeesController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/show")
    public String employees(@RequestParam(name = "page", defaultValue = "0")int page,
                            @RequestParam(name = "size", defaultValue = "10")int size,
                            Model model){
        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employees = employeeService.findAll(pageable);
        model.addAttribute("employees", employees);
        model.addAttribute("employeesNumber", employees.getTotalElements());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", employees.getTotalPages());

        return "employee/employees";
    }

    @GetMapping("/profile/{id}")
    public String profile(@PathVariable Integer id, Model model){
        Optional<Employee> optionalEmployee = employeeService.findById(id);
        Employee employee = optionalEmployee.orElse(null);

        model.addAttribute("employee", employee);
        return "employee/profile";
    }
}
