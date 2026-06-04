package com.cool.spring.service;

import com.cool.spring.dao.entity.Book;
import com.cool.spring.dao.repository.BookRepo;
import com.cool.spring.exception.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepo repo;

    @Transactional(readOnly = true)
    public Book getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new BookNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Book> getAll() {
        return repo.findAll();
    }

    @Transactional
    public Book create(Book book) {
        return repo.save(book);
    }

    @Transactional
    public Book update(Long id, Book data) {

        Book existing = getById(id);

        existing.setTitle(data.getTitle());
        existing.setAuthor(data.getAuthor());
        existing.setPublicationYear(data.getPublicationYear());

        repo.update(existing);

        return existing;
    }

    @Transactional
    public void delete(Long id) {

        if (repo.findById(id).isEmpty()) {
            throw new BookNotFoundException(id);
        }

        repo.deleteById(id);
    }
}
