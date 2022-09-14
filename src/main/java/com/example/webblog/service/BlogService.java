package com.example.webblog.service;

import com.example.webblog.Repository.BlogRepository;
import com.example.webblog.model.Blog;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    public List<Blog> getAll() {
        return (List<Blog>) blogRepository.findAll();
    }

    public Blog add(Blog blog) {
        return blogRepository.save(blog);
    }

    public Boolean delete(long id) {
        try {
            blogRepository.deleteById(id);
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            return false;
        }
        return true;
    }

    public Blog update(long id, Blog blog) {
        Blog blog1 = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Blog not exits " + id));
        if (blog1 != null) {
            blog1.setTitle(blog.getTitle());
            blog1.setCovers(blog.getCovers());
            blog1.setContent(blog.getContent());
            return blogRepository.save(blog1);
        }
        return null;
    }

    public Blog getBlog(long id) {
        return blogRepository.findById(id).get();
    }
}
