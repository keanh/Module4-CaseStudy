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

@Controller
public class BlogController {
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

    @GetMapping(value = "blog/view")
    public ModelAndView listBlogs(@RequestParam("s") Optional<String> s, Pageable pageable, Principal principal){
        Page<Blog> blogs;
        if (s.isPresent()){
            blogs = blogService.findAllByTittle(s.get(),pageable);
        }else {
            blogs = blogService.findAll(pageable);
        }

        ModelAndView modelAndView = new ModelAndView("blog/home");
        modelAndView.addObject("blogs",blogs);
        if (principal != null){
            Account account  = accountService.findAccountByUsername(principal.getName());
            modelAndView.addObject("account",account);
        }else {
            modelAndView.addObject("account",new Account());
        }
        return modelAndView;
    }

//    @GetMapping(value = "blogRestful/view")
//    public ResponseEntity<Page<Blog>> listBlogsRestful(@RequestParam("s") Optional<String> s, Pageable pageable, Principal principal){
//        Page<Blog> blogs;
//        if (s.isPresent()){
//            blogs = blogService.findAllByTittle(s.get(),pageable);
//        }else {
//            blogs = blogService.findAll(pageable);
//        }
//        if (blogs.isEmpty()){
//            return new ResponseEntity<Page<Blog>>(HttpStatus.NO_CONTENT);
//        }else {
//            return new ResponseEntity<Page<Blog>>(blogs,HttpStatus.OK);
//        }
//    }

    @GetMapping(value = "user/search/{id}")
    public ModelAndView searchBlog(@PathVariable Long id,Principal principal){
        Optional<Blog> blog = blogService.findById(id);
        if (blog.isPresent()){
            ModelAndView modelAndView = new ModelAndView("blog/view");
            modelAndView.addObject("blog",blog.get());
            if (principal != null){
                Account account  = getCurrentAccount(principal);
                modelAndView.addObject("account",account);
            }else {
                modelAndView.addObject("account",new Account());
            }
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("error");
            return modelAndView;
        }
    }

//    @GetMapping(value = "searchRestful/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Blog> searchBlogRestful(@PathVariable Long id){
//        Optional<Blog> blog = blogService.findById(id);
//        if (blog == null){
//            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
//        }else {
//            return new ResponseEntity<Blog>(blog.get(),HttpStatus.OK);
//        }
//    }

    @GetMapping("user/create")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("blog/create");
        modelAndView.addObject("blog",new Blog());
        return modelAndView;
    }

    @PostMapping("user/create")
    public String createBlog(Principal principal,@Validated @ModelAttribute("blog") Blog blog, BindingResult bindingResult){
        if (bindingResult.hasFieldErrors()){
            return "redirect:user/create";
        }else {
            Account account = getCurrentAccount(principal);
            blog.setAccount(account);
            blogService.save(blog);
//            modelAndView.addObject("message","New blog created successfully");
            return "redirect:blog/view";
        }
    }

//    @PostMapping(value = "createRestful",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Void> createBlogRestful(@RequestBody Blog blog){
//        blogService.save(blog);
//        HttpHeaders headers = new HttpHeaders();
//        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
//    }

    @GetMapping("user/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Optional<Blog> blog = blogService.findById(id);
        if(blog.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("blog/edit");
            modelAndView.addObject("blog", blog.get());
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("error");
            return modelAndView;
        }
    }

    @PostMapping("user/edit")
    public ModelAndView updateBlog(@ModelAttribute("blog") Blog blog){
        Optional<Blog> blog1 = blogService.findById(blog.getId());
        Blog blog2 = blog1.get();
        blog2.setTittle(blog.getTittle());
        blog2.setCategory(blog.getCategory());
        blog2.setPermissionView(blog.getPermissionView());
        blog2.setPicture(blog.getPicture());
        blog2.setContent(blog.getContent());
        if (blog2 != null){
            blogService.save(blog);
            ModelAndView modelAndView = new ModelAndView("blog/edit");
            modelAndView.addObject("blog", blog);
            modelAndView.addObject("message","Blog updated successfully");
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("error");
            return modelAndView;
        }
    }

//    @PutMapping("editRestful/{id}")
//    public ResponseEntity<Blog> updateBlogRestful(@PathVariable Long id,@RequestBody Blog blog){
//        Optional<Blog> blog1 = blogService.findById(blog.getId());
//        Blog blog2 = blog1.get();
//
//        if (blog2 == null){
//            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
//        }else {
//            blog2.setTittle(blog.getTittle());
//            blog2.setCategory(blog.getCategory());
//            blog2.setPermissionView(blog.getPermissionView());
//            blog2.setPicture(blog.getPicture());
//            blog2.setContent(blog.getContent());
//            blogService.save(blog);
//            return new ResponseEntity<Blog>(blog2,HttpStatus.OK);
//        }
//    }

    @GetMapping("user/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Optional<Blog> blog = blogService.findById(id);
        if(blog.isPresent()) {
            Blog blog1 = blog.get();
            blogService.remove(blog1.getId());
            Long accountId = blog1.getAccount().getId();
            ModelAndView modelAndView = new ModelAndView("redirect:/user/all_blogs/" + accountId);
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("error");
            return modelAndView;
        }

    }

//    @DeleteMapping(value = "deleteBlogRestful/{id}")
//    public ResponseEntity<Blog> deleteBlogRestful(@PathVariable Long id){
//        Optional<Blog> blog = blogService.findById(id);
//        if (blog == null){
//            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
//        }else {
//            blogService.remove(id);
//            return new ResponseEntity<Blog>(HttpStatus.NO_CONTENT);
//        }
//    }

    @GetMapping("user/all_blogs/{id}")
    public ModelAndView showAllBlogs(@PathVariable Long id,Pageable pageable,Principal principal){
        ModelAndView modelAndView = new ModelAndView("blog/home");
        Page<Blog> blogs = blogService.findBlogsByAccount_id(id,pageable);
        modelAndView.addObject("blogs",blogs);
        if (principal != null){
            Account account  = accountService.findAccountByUsername(principal.getName());
            modelAndView.addObject("account",account);
        }else {
            modelAndView.addObject("account",new Account());
        }
        return modelAndView;
    }

//    @GetMapping("all_blogsRestful/{id}")
//    public ResponseEntity<Page<Blog>> showAllBlogsRestful(@PathVariable Long id,Pageable pageable,Principal principal){
//        Page<Blog> blogs = blogService.findBlogsByAccount_id(id,pageable);
//        if (blogs == null){
//            return new ResponseEntity<Page<Blog>>(HttpStatus.NOT_FOUND);
//        }else {
//            return new ResponseEntity<Page<Blog>>(blogs,HttpStatus.OK);
//        }
//    }

    @GetMapping("user/search-by-category/{name}/{id}")
    public ModelAndView showBlogByCategory(@PathVariable("name") String name,@PathVariable("id") Long id,Pageable pageable,Principal principal){
        ModelAndView modelAndView = new ModelAndView("blog/home");
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        Page<Blog> blogs = blogService.findBlogsByCategory(category,pageable);
        modelAndView.addObject("blogs",blogs);
        if (principal != null){
            Account account  = accountService.findAccountByUsername(principal.getName());
            modelAndView.addObject("account",account);
        }else {
            modelAndView.addObject("account",new Account());
        }
        return  modelAndView;
    }

//    @RequestMapping(value = "/blogs/", method = RequestMethod.GET)
//    public ResponseEntity<List<Blog>> showAllBlogs(){
//        List<Blog> blogs = blogService.findAllBlogsResful();
//        if (blogs.isEmpty()){
//            return new ResponseEntity<List<Blog>>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<List<Blog>>(blogs,HttpStatus.OK);
//    }

//    @RequestMapping(value = "/blogs-view/{id}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Blog> showBlog(@PathVariable("id") Long id){
//        Blog blog = blogService.findById(id);
//        if (blog == null){
//            return new ResponseEntity<Blog>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<Blog>(blog,HttpStatus.OK);
//    }

    //    @PostMapping(value = "/search-blogs")
//    @ResponseBody
//    public ResponseEntity<List<Blog>> search(@RequestBody Blog blog){
//        List<Blog> blogs = blogService.findAllByName(blog.getName());
//        return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
//    }
}
