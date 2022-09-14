package com.example.webblog.Repository;

import com.example.webblog.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository <Author, Long> {
    List<Author> findByNameContaining(String name);
    Page<Author> findByNameContaining(String name, Pageable pageable);
}
