DROP TABLE IF EXISTS books;

CREATE TABLE books (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       author VARCHAR(255) NOT NULL,
                       publication_year VARCHAR(4) NOT NULL
);