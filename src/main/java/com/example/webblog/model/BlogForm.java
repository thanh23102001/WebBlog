package com.example.webblog.model;

import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Data
@Validated
public class BlogForm {
    private Long id;
    @NotEmpty
    @Size(min = 3,max = 20,message = "Min is 3 value")
    private String title;
    @NotEmpty
    private String content;
    private Set<CoverForm> coverForms;

    private Category category;

    private Author author;

    private MultipartFile[] files;

    public BlogForm() {
    }

    public BlogForm(BlogFormBuilder blogFormBuilder) {
        this.title = blogFormBuilder.title;
        this.content = blogFormBuilder.content;
        this.coverForms = blogFormBuilder.coverForms;
    }

    public static class BlogFormBuilder {

        private final String title;
        private String content;
        private Set<CoverForm> coverForms;
        private Category category;

        private  Author author;

        public BlogFormBuilder(String title) {
            this.title = title;
        }

        public  BlogFormBuilder content(String content) {
            this.content = content;
            return this;
        }

        public  BlogFormBuilder cover(Set<CoverForm> cover) {
            this.coverForms = cover;
            return this;
        }

        public BlogFormBuilder category(Category category) {
            this.category = category;
            return this;
        }

        public BlogFormBuilder author(Author author) {
            this.author = author;
            return this;
        }

        public BlogForm build() {
            return new BlogForm(this);
        }
    }
}
