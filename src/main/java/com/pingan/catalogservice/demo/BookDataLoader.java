package com.pingan.catalogservice.demo;

import com.pingan.catalogservice.domain.Book;
import com.pingan.catalogservice.domain.BookRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("testdata") //将该类分配给testdata profile，它仅在testdata profile处于激活状态时才会注册
public class BookDataLoader {
    private final BookRepository bookRepository;

    public BookDataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData(){
        var book1 = new Book("1234567891","Northern Lights","Lyra Silverstar",9.90);
        var book2 = new Book("1234567892","Polar Journey","lorek Polarson",12.90);
        bookRepository.save(book1);
        bookRepository.save(book2);
    }

}
