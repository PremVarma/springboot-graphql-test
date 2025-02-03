package com.test.graphql.repository;

import com.test.graphql.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findByAuthor(String author);
    List<Book> findByGenre(String genre);
}
