package com.example.webblog.Controller;

import com.example.webblog.model.BlogForm;
import com.example.webblog.model.Category;
import com.example.webblog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public String listCategories(Model model){
        model.addAttribute("categories", categoryService.getAllCategories());
        return "listCategory";
    }

//    @PostMapping("/category/add")
//    public String add(Model model,@ModelAttribute("category") Category categorys) {
//        categorys = categoryService.getCategory().getId();
//        category.setCategory(categorys);
//        blogService.add(blog);
//        return "add";
//    }
}
