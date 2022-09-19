package com.example.webblog.Controller;

import com.example.webblog.model.*;
import com.example.webblog.service.AuthorService;
import com.example.webblog.service.BlogService;
import com.example.webblog.service.CategoryService;
import com.example.webblog.service.CoverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Slf4j
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CoverService coverService;

    @Value("${upload.path}")
    private String fileUpload;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("blogs", blogService.getAll());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "index";
    }

    @GetMapping("/{id}")
    public String getBlogByCategory(@PathVariable(value = "id") Long id, Model model) {
        System.out.println("id=" + id);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("blogs", categoryService.getCategory(id).getBlogs());
        return "index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("blog", new BlogForm());
        System.out.println(categoryService.getAllCategories());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "add";
    }

    @PostMapping("/add")
    public String add(@Validated @ModelAttribute("blog") BlogForm blogForm, Errors errors,
                      Model model) {
        System.out.println("add blog");
//        System.out.println("file: " + blogForm.getFiles().toArray().toString());

        if (errors.hasErrors()) {
//            model.addAttribute("blog", blogForm);
//            model.addAttribute("categories", categoryService.getAllCategories());
            return "add";
        }
        Blog blog = new Blog.BlogBuilder(blogForm.getTitle())
                .content(blogForm.getContent())
                .build();
        Category category = categoryService.getCategory(blogForm.getCategory().getId());
        blog.setCategory(category);
        blogService.add(blog);

        for (MultipartFile file : blogForm.getFiles()) {
//            String fileName = file.getOriginalFilename();
            try {
                var fileName = file.getOriginalFilename();
                var is = file.getInputStream();

                Files.copy(is, Paths.get(this.fileUpload + fileName), StandardCopyOption.REPLACE_EXISTING);
                Cover cover = new Cover(fileName, blog);
//                FileCopyUtils.copy(file.getName().getBytes(), new File(this.fileUpload + fileName));
                System.out.println("file: " + fileName);
//                cover.setBlog(blog);
                coverService.save(cover);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/";
    }

    @DeleteMapping(path = "/{id}/delete")
    public @ResponseBody Boolean delete(@PathVariable("id") long id) {
        if (blogService.delete(id)) {
            return true;
        }
        return false;
    }

    @GetMapping(path = "/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("blog", blogService.getBlog(id));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "edit";
    }

    @PostMapping(path = "/edit")
    public String edit(@ModelAttribute("blog") Blog blog,
                       BindingResult result,
                       Model model) {

        if (result.hasErrors()) {
            model.addAttribute("blog", blog);
            return "edit";
        }
        blogService.update(blog.getId(), blog);
        return "redirect:/";
    }

    @GetMapping("search/paginated")
    public String search (ModelMap model,
                          @RequestParam (name="name", required = false)String name, @RequestParam("page")Optional<Integer>page,@RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("name"));
        Page<Author> resultPage = null;

        if(StringUtils.hasText(name)){
            resultPage = authorService.findByNameContaining(name, pageable);
            model.addAttribute("name",name);
        }else{
            resultPage = authorService.findAll(pageable);
        }

        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0){
            int start = Math.max(1, currentPage - 2);
            int end = Math.min(currentPage + 2, totalPages);
            if(totalPages > 5){
                if(end == totalPages) start = end -5;
                else if (start == 1) end = start + 5;
            }
            List<Integer> pageNumbers = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("authorPage", resultPage);
        return "/searchpaginated";
    }

    @GetMapping("/403")
    public String error403(){
        return "403";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
}
