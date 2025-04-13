package com.pingan.catalogservice.demo;

import com.pingan.catalogservice.domain.Book;
import com.pingan.catalogservice.domain.BookRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("testdata") //将该类分配给testdata profile，它仅在testdata profile处于激活状态时才会注册
public class BookDataLoader {
    private final BookRepository bookRepository;

    public BookDataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData() {
        bookRepository.deleteAll();
        var book1 = Book.of("1234567895", "Northern Lights", "Lyra Silverstar", 9.90, "Polarsophia");
        var book2 = Book.of("1234567896", "Polar Journey", "lorek Polarson", 12.90, "Polarsophia");
        bookRepository.saveAll(List.of(book1, book2));
    }

}
