package com.example.webblog.Repository;

import com.example.webblog.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
