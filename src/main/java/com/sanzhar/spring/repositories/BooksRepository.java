package com.sanzhar.spring.repositories;

import com.sanzhar.spring.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByOwnerId(int id);
    List <Book> findByNameContainingIgnoreCase(String startString);

}
