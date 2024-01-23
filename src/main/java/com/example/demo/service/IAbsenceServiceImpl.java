package com.example.demo.service;

import com.example.demo.model.Absence;
import com.example.demo.model.Employee;
import com.example.demo.repository.IAbsenceRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IAbsenceServiceImpl implements IAbsenceService {

    private final IAbsenceRepo absenceRepo;

    public IAbsenceServiceImpl(IAbsenceRepo absenceRepo) {
        this.absenceRepo = absenceRepo;
    }

    @Override
    public Optional<Absence> findById(Integer id) {
        return absenceRepo.findById(id);
    }

    @Override
    public Absence save(Absence absence) {
        return absenceRepo.save(absence);
    }

    @Override
    public void deleteById(Integer id) {
        absenceRepo.deleteById(id);
    }

    @Override
    public void update(Absence absence) {
        absenceRepo.save(absence);
    }

    @Override
    public Page<Absence> findAll(Pageable pageable) {
        return absenceRepo.findAll(pageable);
    }

    @Override
    public Page<Absence> findByEmployee(Employee employee, Pageable pageable) {
        return absenceRepo.findByEmployee(employee, pageable);
    }
}
