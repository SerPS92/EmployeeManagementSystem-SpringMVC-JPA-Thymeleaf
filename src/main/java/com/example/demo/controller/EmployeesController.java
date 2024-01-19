package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.service.IEmployeeService;
import com.example.demo.service.UploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/employees")
public class EmployeesController {

    private final Logger log = LoggerFactory.getLogger(EmployeesController.class);

    private final IEmployeeService employeeService;
    private final UploadFileService uploadFileService;

    public EmployeesController(IEmployeeService employeeService,
                               UploadFileService uploadFileService) {
        this.employeeService = employeeService;
        this.uploadFileService = uploadFileService;
    }

    @GetMapping("/show")
    public String employees(@RequestParam(name = "page", defaultValue = "0")int page,
                            @RequestParam(name = "size", defaultValue = "10")int size,
                            @RequestParam(name = "field", defaultValue = "No") String field,
                            Model model){
        Sort sort = Sort.by(field);
        Pageable pageable;
        if(!field.equals("No")){
            pageable = PageRequest.of(page, size, sort);
        } else {
            pageable = PageRequest.of(page, size);
        }
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

    @GetMapping("/create")
    public String create(){
        return "employee/create";
    }

    @PostMapping("/save")
    public String save(Employee employee,
                       @RequestParam("img")MultipartFile file) throws IOException {
        if(employee.getId() == null){
            String nameImage = uploadFileService.saveImage(file);
            employee.setImage(nameImage);
        }
        employeeService.save(employee);
        return "redirect:/employees/show";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model,
                       @PathVariable(name = "id")Integer id) {
        Optional<Employee> optionalEmployee = employeeService.findById(id);
        Employee employee = optionalEmployee.orElse(null);
        model.addAttribute("employee", employee);
        return "employee/edit";
    }

    @PostMapping("/update")
    public String update(Employee employee,
                         @RequestParam("img") MultipartFile file) throws IOException {
        if(!file.isEmpty()){
            String nameImage = uploadFileService.saveImage(file);
            employee.setImage(nameImage);
        }
        employeeService.save(employee);
        return "redirect:/employees/show";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id")Integer id){
        Employee employee = new Employee();
        employee = employeeService.findById(id).get();
        if(!employee.getImage().equals("default.jpg")){
            uploadFileService.deleteImage(employee.getImage());
        }
        employeeService.deleteById(id);
        return "redirect:/employees/show";
    }
}
