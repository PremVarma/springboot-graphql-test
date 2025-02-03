package com.test.graphql.service;

import com.test.graphql.entity.Book;
import com.test.graphql.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book testBook;
    private List<Book> testBooks;

    @BeforeEach
    void setUp() {
        testBook = new Book("1", "Test Book", "Test Author", 2023, "Fiction", 29.99);
        Book book2 = new Book("2", "Another Book", "Test Author", 2024, "Non-Fiction", 39.99);
        testBooks = Arrays.asList(testBook, book2);
    }

    @Test
    void getAllBooks_ShouldReturnAllBooks() {
        // Given
        when(bookRepository.findAll()).thenReturn(testBooks);

        // When
        List<Book> result = bookService.getAllBooks();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyElementsOf(testBooks);
        verify(bookRepository).findAll();
    }

    @Test
    void getBookById_WhenBookExists_ShouldReturnBook() {
        // Given
        when(bookRepository.findById("1")).thenReturn(Optional.of(testBook));

        // When
        Optional<Book> result = bookService.getBookById("1");

        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testBook);
        verify(bookRepository).findById("1");
    }

    @Test
    void getBookById_WhenBookDoesNotExist_ShouldReturnEmpty() {
        // Given
        when(bookRepository.findById("999")).thenReturn(Optional.empty());

        // When
        Optional<Book> result = bookService.getBookById("999");

        // Then
        assertThat(result).isEmpty();
        verify(bookRepository).findById("999");
    }

    @Test
    void getBooksByAuthor_ShouldReturnBooksForAuthor() {
        // Given
        when(bookRepository.findByAuthor("Test Author")).thenReturn(testBooks);

        // When
        List<Book> result = bookService.getBooksByAuthor("Test Author");

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyElementsOf(testBooks);
        verify(bookRepository).findByAuthor("Test Author");
    }

    @Test
    void getBooksByGenre_ShouldReturnBooksForGenre() {
        // Given
        List<Book> fictionBooks = Arrays.asList(testBook);
        when(bookRepository.findByGenre("Fiction")).thenReturn(fictionBooks);

        // When
        List<Book> result = bookService.getBooksByGenre("Fiction");

        // Then
        assertThat(result).hasSize(1);
        assertThat(result).containsExactlyElementsOf(fictionBooks);
        verify(bookRepository).findByGenre("Fiction");
    }

    @Test
    void createBook_ShouldSaveAndReturnBook() {
        // Given
        Book newBook = new Book(null, "New Book", "New Author", 2024, "Fiction", 19.99);
        Book savedBook = new Book("3", "New Book", "New Author", 2024, "Fiction", 19.99);
        when(bookRepository.save(newBook)).thenReturn(savedBook);

        // When
        Book result = bookService.createBook(newBook);

        // Then
        assertThat(result).isEqualTo(savedBook);
        verify(bookRepository).save(newBook);
    }

    @Test
    void updateBook_ShouldUpdateAndReturnBook() {
        // Given
        Book updateBook = new Book("1", "Updated Book", "Updated Author", 2024, "Fiction", 39.99);
        when(bookRepository.save(updateBook)).thenReturn(updateBook);

        // When
        Book result = bookService.updateBook("1", updateBook);

        // Then
        assertThat(result).isEqualTo(updateBook);
        verify(bookRepository).save(updateBook);
    }

    @Test
    void deleteBook_ShouldDeleteBook() {
        // Given
        doNothing().when(bookRepository).deleteById("1");

        // When
        bookService.deleteBook("1");

        // Then
        verify(bookRepository).deleteById("1");
    }
}