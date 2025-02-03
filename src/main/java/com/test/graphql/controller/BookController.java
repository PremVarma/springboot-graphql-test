package com.test.graphql.controller;

import com.test.graphql.entity.Book;
import com.test.graphql.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @QueryMapping
    public List<Book> books() {
        return bookService.getAllBooks();
    }

    @QueryMapping
    public Book book(@Argument String id) {
        return bookService.getBookById(id).orElse(null);
    }

    @QueryMapping
    public List<Book> booksByAuthor(@Argument String author) {
        return bookService.getBooksByAuthor(author);
    }

    @QueryMapping
    public List<Book> booksByGenre(@Argument String genre) {
        return bookService.getBooksByGenre(genre);
    }

    @MutationMapping
    public Book createBook(@Argument String title, @Argument String author,
                           @Argument Integer publishYear, @Argument String genre,
                           @Argument Double price) {
        Book book = new Book(title, author, publishYear, genre, price);
        return bookService.createBook(book);
    }

    @MutationMapping
    public Book updateBook(@Argument String id, @Argument String title,
                           @Argument String author, @Argument Integer publishYear,
                           @Argument String genre, @Argument Double price) {
        Book book = new Book(id, title, author, publishYear, genre, price);
        return bookService.updateBook(id, book);
    }

    @MutationMapping
    public Boolean deleteBook(@Argument String id) {
        bookService.deleteBook(id);
        return true;
    }
}