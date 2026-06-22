INSERT INTO departments(id, name)
VALUES
    (1, 'IT'),
    (2, 'HR');

INSERT INTO employees(
    id,
    first_name,
    last_name,
    position,
    salary,
    department_id
)
VALUES
    (1, 'Ivan', 'Ivanov', 'Developer', 150000, 1),
    (2, 'Petr', 'Petrov', 'QA Engineer', 120000, 1),
    (3, 'Anna', 'Sidorova', 'HR Manager', 100000, 2);