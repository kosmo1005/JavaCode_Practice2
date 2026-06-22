package com.cool.spring.dao.repository;

import com.cool.spring.dao.entity.Employee;
import com.cool.spring.dao.projection.EmployeeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<EmployeeProjection> findProjectionById(Long id);
}
