package com.cool.spring.service;

import com.cool.spring.dao.entity.Employee;
import com.cool.spring.dao.projection.EmployeeProjection;
import com.cool.spring.dao.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository repo;

    public List<Employee> getAll() {
        return repo.findAll();
    }

    public Employee getDetailById(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Employee not found: " + id));
    }

    public EmployeeProjection getBasicById(Long id) {
        return repo.findProjectionById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Employee not found: " + id));
    }

    @Transactional
    public Employee create(Employee employee) {
        employee.setId(null);
        return repo.save(employee);
    }

    @Transactional
    public Employee update(Long id, Employee data) {

        Employee employee = repo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Employee not found: " + id));

        employee.setFirstName(data.getFirstName());
        employee.setLastName(data.getLastName());
        employee.setPosition(data.getPosition());
        employee.setSalary(data.getSalary());
        employee.setDepartment(data.getDepartment());

        return repo.save(employee);
    }

    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
