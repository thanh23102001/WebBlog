package com.example.webblog.model;

import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Validated
public class Blog {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private long id;

    @NotNull
    @Size(min = 3,max = 20,message = "Min is 3 value")
    private String title;
//    @NotNull
    private String content;

    @ManyToOne
    @JoinColumn(name ="category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name ="author_id")
    private Author author;

    @OneToMany(mappedBy = "blog")
//    @NotNull
    private Set<Cover> covers;

    @Transient
    private List<MultipartFile> files;


    public Blog(){}

    public Blog(long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public Blog(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Set<Cover> getCovers() {
        return covers;
    }

    public void setCovers(Set<Cover> covers) {
        this.covers = covers;
    }

    public Blog(BlogBuilder blogBuilder) {
        this.title = blogBuilder.title;
        this.content = blogBuilder.content;
    }

    public static class BlogBuilder {
        private final String title;
        private String content;
        private Category category;

        private Author author;

        public BlogBuilder(String title) {
            this.title = title;
        }

        public BlogBuilder content(String content) {
            this.content = content;
            return this;
        }

        public BlogBuilder category(Category category) {
            this.category = category;
            return this;
        }

        public BlogBuilder author(Author author){
            this.author = author;
            return this;
        }

        public Blog build() {
            return new Blog(this);
        }
    }
}





