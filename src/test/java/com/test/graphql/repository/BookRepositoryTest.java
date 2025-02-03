package com.test.graphql.repository;
import com.test.graphql.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void findById_WhenBookExists_ShouldReturnBook() {
        // Given
        Book book = new Book(null, "Test Book", "Test Author", 2023, "Fiction", 29.99);
        Book savedBook = entityManager.persistFlushFind(book);

        // When
        Optional<Book> found = bookRepository.findById(savedBook.getId());

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo(book.getTitle());
    }

    @Test
    void findByAuthor_ShouldReturnBooksForAuthor() {
        // Given
        Book book1 = new Book(null, "Book 1", "Test Author", 2023, "Fiction", 29.99);
        Book book2 = new Book(null, "Book 2", "Test Author", 2024, "Non-Fiction", 39.99);
        Book book3 = new Book(null, "Book 3", "Other Author", 2024, "Fiction", 19.99);

        entityManager.persist(book1);
        entityManager.persist(book2);
        entityManager.persist(book3);
        entityManager.flush();

        // When
        List<Book> found = bookRepository.findByAuthor("Test Author");

        // Then
        assertThat(found).hasSize(2);
        assertThat(found).extracting(Book::getTitle).containsExactlyInAnyOrder("Book 1", "Book 2");
    }

    @Test
    void findByGenre_ShouldReturnBooksForGenre() {
        // Given
        Book book1 = new Book(null, "Book 1", "Author 1", 2023, "Fiction", 29.99);
        Book book2 = new Book(null, "Book 2", "Author 2", 2024, "Fiction", 39.99);
        Book book3 = new Book(null, "Book 3", "Author 3", 2024, "Non-Fiction", 19.99);

        entityManager.persist(book1);
        entityManager.persist(book2);
        entityManager.persist(book3);
        entityManager.flush();

        // When
        List<Book> found = bookRepository.findByGenre("Fiction");

        // Then
        assertThat(found).hasSize(2);
        assertThat(found).extracting(Book::getTitle).containsExactlyInAnyOrder("Book 1", "Book 2");
    }

    @Test
    void save_ShouldPersistBook() {
        // Given
        Book book = new Book(null, "New Book", "New Author", 2024, "Fiction", 29.99);

        // When
        Book saved = bookRepository.save(book);

        // Then
        Book found = entityManager.find(Book.class, saved.getId());
        assertThat(found).isNotNull();
        assertThat(found.getTitle()).isEqualTo("New Book");
    }

    @Test
    void delete_ShouldRemoveBook() {
        // Given
        Book book = new Book(null, "Test Book", "Test Author", 2023, "Fiction", 29.99);
        Book savedBook = entityManager.persist(book);
        entityManager.flush();

        // When
        bookRepository.deleteById(savedBook.getId());

        // Then
        Book found = entityManager.find(Book.class, savedBook.getId());
        assertThat(found).isNull();
    }
}