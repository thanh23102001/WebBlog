package com.example.webblog.service;

import com.example.webblog.Repository.CategoryRepository;
import com.example.webblog.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return (List<Category>) categoryRepository.findAll();
    }

    public Category getCategory (Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Khong ton tai category id: " + id));
    }
}
