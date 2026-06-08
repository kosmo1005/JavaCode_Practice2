package com.cool.spring.dao.repository;

import com.cool.spring.dao.entity.Book;
import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepo {

    private final JdbcTemplate jdbcTemplate;

    public Book save(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    """
                    INSERT INTO books(title, author, publication_year)
                    VALUES (?, ?, ?)
                    """,
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getPublicationYear());

            return ps;
        }, keyHolder);

        book.setId(keyHolder.getKey().longValue());

            return book;
    }

    public Optional<Book> findById(Long id) {

        String sql = """
                SELECT id, title, author, publication_year
                FROM books
                WHERE id = ?
                """;

        List<Book> books = jdbcTemplate.query(
                sql,
                this::parseResultSet,
                id
        );

        return books.stream().findFirst();
    }

    public List<Book> findAll() {

        String sql = """
                SELECT id, title, author, publication_year
                FROM books
                """;

        return jdbcTemplate.query(sql, this::parseResultSet);
    }

    public void deleteById(Long id) {

        String sql = """
                DELETE FROM books
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }

    public int update(Book book) {

        String sql = """
                UPDATE books
                SET title = ?,
                    author = ?,
                    publication_year = ?
                WHERE id = ?
                """;

        return jdbcTemplate.update(
                sql,
                book.getTitle(),
                book.getAuthor(),
                book.getPublicationYear(),
                book.getId()
        );
    }

    private Book parseResultSet(ResultSet rs, int rowNum) throws SQLException {

        return Book.builder()
                .id(rs.getLong("id"))
                .title(rs.getString("title"))
                .author(rs.getString("author"))
                .publicationYear(rs.getString("publication_year"))
                .build();
    }
}
