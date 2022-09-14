package com.example.webblog.service;

import com.example.webblog.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface AuthorService {
    Page<Author> findByNameContaining (String name, Pageable pageable);
    Page<Author> findAll(Pageable pageable);
}
