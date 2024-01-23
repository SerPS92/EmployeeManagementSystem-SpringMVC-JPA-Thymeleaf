package com.example.demo.controller;

import com.example.demo.model.Absence;
import com.example.demo.model.Employee;
import com.example.demo.repository.IAbsenceRepo;
import com.example.demo.service.IAbsenceService;
import com.example.demo.service.IEmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/absences")
public class AbsencesController {

    private final Logger log = LoggerFactory.getLogger(AbsencesController.class);
    private final IAbsenceService absenceService;
    private final IEmployeeService employeeService;

    public AbsencesController(IAbsenceService absenceService,
                              IEmployeeService employeeService) {
        this.employeeService = employeeService;
        this.absenceService = absenceService;
    }

    @GetMapping("/show")
    public String show(@RequestParam(name = "page", defaultValue = "0")int page,
                       @RequestParam(name = "size", defaultValue = "8")int size,
                       Model model){

        Pageable pageable = PageRequest.of(page, size);
        Page<Absence> absences = absenceService.findAll(pageable);

        model.addAttribute("absences", absences);
        model.addAttribute("absencesNumber", absences.getTotalElements());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", absences.getTotalPages());
        return "absence/absences";
    }

    @GetMapping("/absenceEmploy/{id}")
    public String absenceEmploy(@PathVariable(name = "id")Integer id,
                                @RequestParam(name = "page", defaultValue = "0")int page,
                                @RequestParam(name = "size", defaultValue = "8")int size,
                                Model model){
        Pageable pageable = PageRequest.of(page, size);
        Employee employee = employeeService.findById(id).orElse(null);
        Page<Absence> absences = absenceService.findByEmployee(employee, pageable);
        model.addAttribute("absences", absences);
        model.addAttribute("employee", employee);
        return "absence/absenceEmploy";
    }

    @GetMapping("/create/{id}")
    public String create(@PathVariable(name = "id")Integer id,
                         Model model){
        Employee employee = employeeService.findById(id).orElse(null);
        model.addAttribute("employee", employee);
        return "absence/create";
    }

    @PostMapping("/save")
    public String save(Absence absence,
                       @RequestParam(name = "employeeId") String id){
        Employee employee = employeeService.findById(Integer.valueOf(id)).orElse(null);
        absence.setEmployee(employee);
        absenceService.save(absence);
        return "redirect:/absences/show";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id")Integer id){
        absenceService.deleteById(id);
        return "redirect:/absences/show";
    }

}
