package com.cool.spring.dao.projection;

public interface EmployeeProjection {

    String getFirstName();

    String getLastName();

    String getPosition();

    DepartmentProjection getDepartment();

    default String getFullName() {
        return getFirstName() + " " + getLastName();
    }
}
