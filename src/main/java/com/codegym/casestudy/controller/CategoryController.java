package com.codegym.casestudy.controller;

import com.codegym.casestudy.model.Blog;
import com.codegym.casestudy.model.Category;
import com.codegym.casestudy.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Controller
@RequestMapping("category")
public class CategoryController {
//    @Autowired
//    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/view")
    public ModelAndView listProvinces(){
        Iterable<Category> categories = categoryService.findAll();
        ModelAndView modelAndView = new ModelAndView("category/view");
        modelAndView.addObject("categories",categories);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("category/create");
        modelAndView.addObject("category",new Category());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView saveCategory(@ModelAttribute("category") Category category){
        categoryService.save(category);
        ModelAndView modelAndView = new ModelAndView("category/create");
        modelAndView.addObject("category",new Category());
        modelAndView.addObject("message","New category created successfully");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()){
            ModelAndView modelAndView = new ModelAndView("category/edit");
            modelAndView.addObject("category",category.get());
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("error");
            return modelAndView;
        }
    }

    @PostMapping("/edit")
    public String updateCategory(@ModelAttribute("category") Category category){
        categoryService.save(category);
        return "redirect:/category/view";
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()){
            ModelAndView modelAndView = new ModelAndView("category/delete");
            modelAndView.addObject("category",category.get());
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("error");
            return modelAndView;
        }
    }

    @PostMapping("/delete")
    public String deleteCategory(@ModelAttribute("category") Category category){
        categoryService.remove(category.getId());
        return "redirect:/category/view";
    }

//    @GetMapping("/view/{id}")
//    public ModelAndView viewCategory(@PathVariable Long id){
//        Category category = categoryService.findById(id);
//        if (category == null){
//            return new ModelAndView("/error.404");
//        }
//
//        Iterable<Blog> blogs = blogService.findAllByCategory(category);
//        ModelAndView modelAndView = new ModelAndView("/category/view");
//        modelAndView.addObject("category",category);
//        modelAndView.addObject("blogs",blogs);
//        return modelAndView;
//    }

//    @RequestMapping(value = "/category-blog/{id}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<Blog>> findBlogByCategory(@PathVariable("id") Long id){
//        Category category = categoryService.findById(id);
//        List<Blog> blogs = (List<Blog>) blogService.findAllByCategory(category);
//        return new ResponseEntity<List<Blog>>(blogs,HttpStatus.OK);
//    }

    //    @RequestMapping(value = "/categories/",method = RequestMethod.GET)
//    public ResponseEntity<List<Category>> listCategories(){
//        List<Category> categories = (List<Category>) categoryService.findAll();
//        if (categories.isEmpty()){
//            return new ResponseEntity<List<Category>>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<List<Category>>(categories,HttpStatus.OK);
//    }
}
