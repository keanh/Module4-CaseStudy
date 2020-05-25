package com.codegym.casestudy.controller;

import com.codegym.casestudy.model.Blog;
import com.codegym.casestudy.model.Category;
import com.codegym.casestudy.service.BlogService;
import com.codegym.casestudy.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("blog")
public class BlogContrller {
    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories(){
        return categoryService.findAll();
    }

//    @GetMapping(value = "/testI18N")
//    public ModelAndView I18N(){
//        ModelAndView modelAndView = new ModelAndView("/blog/test");
//        modelAndView.addObject("credential",new Blog());
//        return modelAndView;
//    }

    @GetMapping(value = "/view")
    public ModelAndView listBlogs(@RequestParam("s") Optional<String> s, Pageable pageable){
        Page<Blog> blogs;
        if (s.isPresent()){
            blogs = blogService.findAllByTittle(s.get(),pageable);
        }else {
            blogs = blogService.findAll(pageable);
        }

        ModelAndView modelAndView = new ModelAndView("blog/home");
        modelAndView.addObject("blogs",blogs);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("blog/create");
        modelAndView.addObject("blog",new Blog());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createBlog(@ModelAttribute("blog") Blog blog){
        blogService.save(blog);
        ModelAndView modelAndView = new ModelAndView("blog/create");
        modelAndView.addObject("blog",new Blog());
        modelAndView.addObject("message","New blog created successfully");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
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

    @PostMapping("/edit")
    public ModelAndView updateBlog(@ModelAttribute("blog") Blog blog){
        blogService.save(blog);
        ModelAndView modelAndView = new ModelAndView("blog/edit");
        modelAndView.addObject("blog", blog);
        modelAndView.addObject("message", "Blog updated successfully");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Optional<Blog> blog = blogService.findById(id);
        if(blog.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("blog/delete");
            modelAndView.addObject("blog", blog.get());
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("error");
            return modelAndView;
        }
    }

    @PostMapping("/delete")
    public String deleteBlog(@ModelAttribute("blog") Blog blog){
        blogService.remove(blog.getId());
        return "redirect:/blog/view";
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
