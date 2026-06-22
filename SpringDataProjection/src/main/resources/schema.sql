DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS departments;

CREATE TABLE departments (
                             id BIGINT PRIMARY KEY,
                             name VARCHAR(255)
);

CREATE TABLE employees (
                           id BIGINT PRIMARY KEY,
                           first_name VARCHAR(255),
                           last_name VARCHAR(255),
                           position VARCHAR(255),
                           salary DECIMAL(19,2),
                           department_id BIGINT,

                           CONSTRAINT fk_employee_department
                               FOREIGN KEY (department_id)
                                   REFERENCES departments(id)
);