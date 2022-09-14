package com.example.webblog.service;

import com.example.webblog.Repository.AuthorRepository;
import com.example.webblog.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class AuthorServiceImpl implements AuthorService  {
    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> findByNameContaining(String name) {
        return authorRepository.findByNameContaining(name);
    }

    public Page<Author> findByNameContaining(String name, Pageable pageable) {
        return authorRepository.findByNameContaining(name, pageable);
    }

    public Page<Author> findAll(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }
}
