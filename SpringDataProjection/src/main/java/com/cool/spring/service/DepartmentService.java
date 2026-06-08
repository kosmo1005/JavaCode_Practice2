package com.cool.spring.service;

import com.cool.spring.dao.entity.Department;
import com.cool.spring.dao.repository.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    public Department getById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Department not found: " + id));
    }

    @Transactional
    public Department create(Department department) {
        department.setId(null);
        return departmentRepository.save(department);
    }

    @Transactional
    public Department update(Long id, Department data) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Department not found: " + id));

        department.setName(data.getName());

        return departmentRepository.save(department);
    }

    @Transactional
    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }
}
