package com.codegym.casestudy.controller;

import com.codegym.casestudy.model.Account;
import com.codegym.casestudy.model.Blog;
import com.codegym.casestudy.model.Category;
import com.codegym.casestudy.model.PermissionView;
import com.codegym.casestudy.service.AccountService;
import com.codegym.casestudy.service.BlogService;
import com.codegym.casestudy.service.CategoryService;
import com.codegym.casestudy.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class BlogRestController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private AccountService accountService;

    private Account getCurrentAccount(Principal principal){
        Account account = accountService.findAccountByUsername(principal.getName());
        return account;
    }

    @ModelAttribute("categories")
    public Iterable<Category> categories(){
        return categoryService.findAll();
    }

    @ModelAttribute("permission")
    public Iterable<PermissionView> permissionViews(){
        return permissionService.findAll();
    }

//    @GetMapping(value = "/testI18N")
//    public ModelAndView I18N(){
//        ModelAndView modelAndView = new ModelAndView("/blog/test");
//        modelAndView.addObject("credential",new Blog());
//        return modelAndView;
//    }


    @GetMapping(value = "blogRestful/view")
    public ResponseEntity<Page<Blog>> listBlogsRestful(@RequestParam("s") Optional<String> s, Pageable pageable, Principal principal){
        Page<Blog> blogs;
        if (s.isPresent()){
            blogs = blogService.findAllByTittle(s.get(),pageable);
        }else {
            blogs = blogService.findAll(pageable);
        }
        if (blogs.isEmpty()){
            return new ResponseEntity<Page<Blog>>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<Page<Blog>>(blogs,HttpStatus.OK);
        }
    }

    @GetMapping(value = "searchRestful/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Blog> searchBlogRestful(@PathVariable Long id){
        Optional<Blog> blog = blogService.findById(id);
        if (blog == null){
            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<Blog>(blog.get(),HttpStatus.OK);
        }
    }

    @PostMapping(value = "createRestful",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createBlogRestful(@RequestBody Blog blog){
        blogService.save(blog);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PutMapping("editRestful/{id}")
    public ResponseEntity<Blog> updateBlogRestful(@PathVariable Long id,@RequestBody Blog blog){
        Optional<Blog> blog1 = blogService.findById(blog.getId());
        Blog blog2 = blog1.get();

        if (blog2 == null){
            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
        }else {
            blog2.setTittle(blog.getTittle());
            blog2.setCategory(blog.getCategory());
            blog2.setPermissionView(blog.getPermissionView());
            blog2.setPicture(blog.getPicture());
            blog2.setContent(blog.getContent());
            blogService.save(blog);
            return new ResponseEntity<Blog>(blog2,HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "deleteBlogRestful/{id}")
    public ResponseEntity<Blog> deleteBlogRestful(@PathVariable Long id){
        Optional<Blog> blog = blogService.findById(id);
        if (blog == null){
            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
        }else {
            blogService.remove(id);
            return new ResponseEntity<Blog>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("all_blogsRestful/{id}")
    public ResponseEntity<Page<Blog>> showAllBlogsRestful(@PathVariable Long id,Pageable pageable,Principal principal){
        Page<Blog> blogs = blogService.findBlogsByAccount_id(id,pageable);
        if (blogs == null){
            return new ResponseEntity<Page<Blog>>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<Page<Blog>>(blogs,HttpStatus.OK);
        }
    }

}
