package com.pingan.catalogservice.domain;

import java.util.Optional;

public interface BookRepository {
    Iterable<Book> findAll();

    Optional<Object> findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);

    Book save(Book book);

    void deleteByIsbn(String isbn);


}
