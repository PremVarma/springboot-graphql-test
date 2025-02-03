package com.test.graphql.config;

import com.test.graphql.entity.Book;
import com.test.graphql.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(BookRepository repository) {
        return args -> {
            // Fiction Books
            repository.save(new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, "Fiction", 14.99));
            repository.save(new Book("1984", "George Orwell", 1949, "Fiction", 12.99));
            repository.save(new Book("To Kill a Mockingbird", "Harper Lee", 1960, "Fiction", 15.99));

            // Science Fiction Books
            repository.save(new Book("Dune", "Frank Herbert", 1965, "Science Fiction", 19.99));
            repository.save(new Book("Foundation", "Isaac Asimov", 1951, "Science Fiction", 16.99));

            // Non-Fiction Books
            repository.save(new Book("A Brief History of Time", "Stephen Hawking", 1988, "Non-Fiction", 24.99));
            repository.save(new Book("Sapiens", "Yuval Noah Harari", 2011, "Non-Fiction", 29.99));

            // Fantasy Books
            repository.save(new Book("The Hobbit", "J.R.R. Tolkien", 1937, "Fantasy", 20.99));
            repository.save(new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", 1997, "Fantasy", 18.99));
        };
    }
}
