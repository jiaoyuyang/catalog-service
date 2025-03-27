package com.pingan.catalogservice.domain;

public record Book(String isbn,
                   String titile,
                   String author,
                   Double price) {

}
