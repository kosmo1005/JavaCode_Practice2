package com.cool.spring.function_test;

import com.cool.spring.dao.entity.Employee;
import com.cool.spring.dao.projection.EmployeeProjection;
import com.cool.spring.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    void getDetailById_shouldReturnEmployee() {

        Employee employee = employeeService.getDetailById(1L);

        assertNotNull(employee);
        assertEquals(1L, employee.getId());
        assertEquals("Ivan", employee.getFirstName());
        assertEquals("Ivanov", employee.getLastName());
        assertEquals("Developer", employee.getPosition());

        assertNotNull(employee.getDepartment());
        assertEquals("IT", employee.getDepartment().getName());
    }

    @Test
    void getBasicById_shouldReturnProjection() {

        EmployeeProjection projection =
                employeeService.getBasicById(1L);

        assertNotNull(projection);

        assertEquals("Ivan", projection.getFirstName());
        assertEquals("Ivanov", projection.getLastName());
        assertEquals("Developer", projection.getPosition());
        assertEquals(
                "IT",
                projection.getDepartment().getName()
        );

        assertEquals(
                "Ivan Ivanov",
                projection.getFullName()
        );
    }

    @Test
    void getDetailById_shouldThrowException_whenEmployeeNotExists() {

        assertThrows(
                EntityNotFoundException.class,
                () -> employeeService.getDetailById(999L)
        );
    }

    @Test
    void getBasicById_shouldThrowException_whenEmployeeNotExists() {

        assertThrows(
                EntityNotFoundException.class,
                () -> employeeService.getBasicById(999L)
        );
    }

}
