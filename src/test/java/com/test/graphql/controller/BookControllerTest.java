package com.test.graphql.controller;

import com.test.graphql.entity.Book;
import com.test.graphql.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;
import java.util.Arrays;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@GraphQlTest(BookController.class)
class BookControllerTest {
    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private BookService bookService;

    @Test
    void shouldGetAllBooks() {
        // Given
        Book book1 = new Book("1", "Book 1", "Author 1", 2020, "Fiction", 29.99);
        Book book2 = new Book("2", "Book 2", "Author 2", 2021, "Non-Fiction", 39.99);
        when(bookService.getAllBooks()).thenReturn(Arrays.asList(book1, book2));

        // When/Then
        String query = """
            query {
                books {
                    id
                    title
                    author
                    publishYear
                    genre
                    price
                }
            }
        """;

        graphQlTester.document(query)
                .execute()
                .path("books")
                .entityList(Book.class)
                .hasSize(2);
    }

    @Test
    void shouldGetBookById() {
        // Given
        Book book = new Book("1", "Test Book", "Test Author", 2020, "Fiction", 29.99);
        when(bookService.getBookById("1")).thenReturn(Optional.of(book));

        // When/Then
        String query = """
            query {
                book(id: "1") {
                    id
                    title
                    author
                    publishYear
                    genre
                    price
                }
            }
        """;

        graphQlTester.document(query)
                .execute()
                .path("book")
                .matchesJson("""
                {
                    "id": "1",
                    "title": "Test Book",
                    "author": "Test Author",
                    "publishYear": 2020,
                    "genre": "Fiction",
                    "price": 29.99
                }
            """);
    }

    @Test
    void shouldGetBooksByAuthor() {
        // Given
        Book book = new Book("1", "Test Book", "Test Author", 2020, "Fiction", 29.99);
        when(bookService.getBooksByAuthor("Test Author")).thenReturn(Arrays.asList(book));

        // When/Then
        String query = """
            query {
                booksByAuthor(author: "Test Author") {
                    id
                    title
                    author
                }
            }
        """;

        graphQlTester.document(query)
                .execute()
                .path("booksByAuthor")
                .entityList(Book.class)
                .hasSize(1);
    }

    @Test
    void shouldCreateBook() {
        // Given
        Book newBook = new Book(null, "New Book", "New Author", 2023, "Fiction", 19.99);
        Book savedBook = new Book("1", "New Book", "New Author", 2023, "Fiction", 19.99);
        when(bookService.createBook(any(Book.class))).thenReturn(savedBook);

        // When/Then
        String mutation = """
            mutation {
                createBook(
                    title: "New Book"
                    author: "New Author"
                    publishYear: 2023
                    genre: "Fiction"
                    price: 19.99
                ) {
                    id
                    title
                    author
                    publishYear
                    genre
                    price
                }
            }
        """;

        graphQlTester.document(mutation)
                .execute()
                .path("createBook")
                .matchesJson("""
                {
                    "id": "1",
                    "title": "New Book",
                    "author": "New Author",
                    "publishYear": 2023,
                    "genre": "Fiction",
                    "price": 19.99
                }
            """);
    }

    @Test
    void shouldUpdateBook() {
        // Given
        Book updatedBook = new Book("1", "Updated Book", "Updated Author", 2024, "Non-Fiction", 49.99);
        when(bookService.updateBook(eq("1"), any(Book.class))).thenReturn(updatedBook);

        // When/Then
        String mutation = """
            mutation {
                updateBook(
                    id: "1"
                    title: "Updated Book"
                    author: "Updated Author"
                    publishYear: 2024
                    genre: "Non-Fiction"
                    price: 49.99
                ) {
                    id
                    title
                    author
                    publishYear
                    genre
                    price
                }
            }
        """;

        graphQlTester.document(mutation)
                .execute()
                .path("updateBook")
                .matchesJson("""
                {
                    "id": "1",
                    "title": "Updated Book",
                    "author": "Updated Author",
                    "publishYear": 2024,
                    "genre": "Non-Fiction",
                    "price": 49.99
                }
            """);
    }

    @Test
    void shouldDeleteBook() {
        // Given
        doNothing().when(bookService).deleteBook("1");

        // When/Then
        String mutation = """
            mutation {
                deleteBook(id: "1")
            }
        """;

        graphQlTester.document(mutation)
                .execute()
                .path("deleteBook")
                .entity(Boolean.class)
                .isEqualTo(true);

        verify(bookService).deleteBook("1");
    }
}