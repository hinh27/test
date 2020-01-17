package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.model.Category;
import com.codegym.service.ProductService;
import com.codegym.service.CategoryService;
import com.codegym.service.impl.CategoryServiceImpl;
import com.codegym.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class CategoryController {

    @Autowired
    CategoryService  categoryService;
    @Autowired
    ProductService  productService;



    @GetMapping("/categories")
    public ModelAndView listCategories(){
        Iterable<Category> categories = categoryService.findAll();
        ModelAndView modelAndView = new ModelAndView("/category/list");
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @GetMapping("/create-category")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/category/create");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @PostMapping("/create-category")
    public ModelAndView saveProvince(@ModelAttribute("category") Category category){
        categoryService.save(category);

        ModelAndView modelAndView = new ModelAndView("/category/create");
        modelAndView.addObject("province", new Category());
        modelAndView.addObject("message", "New province created successfully");
        return modelAndView;
    }

        @GetMapping("/edit-category/{id}")
        public ModelAndView showEditForm(@PathVariable Long id){
            Category category = categoryService.findById(id);
            if(category != null) {
                ModelAndView modelAndView = new ModelAndView("/category/edit");
                modelAndView.addObject("category", category);
                return modelAndView;

            }else {
                ModelAndView modelAndView = new ModelAndView("/error.404");
                return modelAndView;
            }
        }

        @PostMapping("/edit-category")
        public ModelAndView updateCategory(@ModelAttribute("category") Category category){
            categoryService.save(category);
            ModelAndView modelAndView = new ModelAndView("/category/edit");
            modelAndView.addObject("category", category);
            modelAndView.addObject("message", "Category updated successfully");
            return modelAndView;
        }

        @GetMapping("/delete-category/{id}")
        public ModelAndView showDeleteForm(@PathVariable Long id){
            Category category = categoryService.findById(id);
            if(category != null) {
                ModelAndView modelAndView = new ModelAndView("/category/delete");
                modelAndView.addObject("category", category);
                return modelAndView;

            }else {
                ModelAndView modelAndView = new ModelAndView("/error.404");
                return modelAndView;
            }
        }

        @PostMapping("/delete-category")
        public String deleteCategory(@ModelAttribute("category") Category category){
            categoryService.remove(category.getId());
            return "redirect:categories";
        }

        @GetMapping("/view-category/{id}")
        public ModelAndView viewCategory(@PathVariable("id") Long id){
            Category category = categoryService.findById(id);
            if(category == null){
                return new ModelAndView("/error.404");
            }

            Iterable<Product> products = productService.findAllByCategory(category);

            ModelAndView modelAndView = new ModelAndView("/category/view");
            modelAndView.addObject("category", category);
            modelAndView.addObject("products", products);
            return modelAndView;
        }
  }

