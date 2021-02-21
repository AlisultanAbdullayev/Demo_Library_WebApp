package com.alijaver.libraryapp.repositories;

import com.alijaver.libraryapp.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(" from Book b " +
            "where lower(concat(b.name, b.seriName, b.author.fullName, b.isbn))" +
            "like lower(concat('%', :keyword, '%')) order by b.name asc ")
    List<Book> findByNameOrBySeriNameOrAuthorOrByIsbn(@Param("keyword") String keyword);
}
