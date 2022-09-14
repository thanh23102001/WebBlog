package com.example.webblog.Repository;

import com.example.webblog.model.Cover;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoverRepository extends CrudRepository<Cover, Long> {
}