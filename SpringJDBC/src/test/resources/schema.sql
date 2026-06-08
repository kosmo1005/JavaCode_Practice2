DROP TABLE IF EXISTS employees;

CREATE TABLE employees (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       author VARCHAR(255) NOT NULL,
                       publication_year VARCHAR(4) NOT NULL
);