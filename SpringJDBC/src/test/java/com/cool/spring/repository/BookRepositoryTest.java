package com.cool.spring.repository;

import com.cool.spring.dao.entity.Book;
import com.cool.spring.dao.repository.BookRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Import(BookRepo.class)
@Sql(
        scripts = {
                "/schema.sql",
                "/data.sql"
        },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
public class BookRepositoryTest {

    @Autowired
    private BookRepo bookRepo;

    @Test
    void saveTest() {

        Book book = Book.builder()
                .title("Java Concurrency in Practice")
                .author("Brian Goetz")
                .publicationYear("2006")
                .build();

        Book saved = bookRepo.save(book);

        assertThat(saved.getId())
                .isNotNull();

        assertThat(saved.getId())
                .isPositive();

        assertThat(bookRepo.findById(saved.getId()))
                .isPresent();

        assertThat(bookRepo.findAll())
                .extracting(Book::getTitle)
                .contains("Java Concurrency in Practice");

        assertThat(bookRepo.findAll())
                .hasSize(4);
    }

    @Test
    void findByIdTest() {

        Optional<Book> book =
                bookRepo.findById(1L);

        assertThat(book).isPresent();

        assertThat(book.get().getTitle())
                .isEqualTo("Clean Code");
    }

    @Test
    void findByIdTest_whenBookNotFound() {

        Optional<Book> book =
                bookRepo.findById(999L);

        assertThat(book).isEmpty();
    }

    @Test
    void findAllTest() {

        List<Book> books =
                bookRepo.findAll();

        assertThat(books)
                .hasSize(3);
    }

    @Test
    void updateTest() {

        Book book =
                bookRepo.findById(1L).orElseThrow();

        book.setTitle("Updated Title");

        int updated =
                bookRepo.update(book);

        assertThat(updated).isEqualTo(1);

        Book reloaded =
                bookRepo.findById(1L).orElseThrow();

        assertThat(reloaded.getTitle())
                .isEqualTo("Updated Title");
    }
}
