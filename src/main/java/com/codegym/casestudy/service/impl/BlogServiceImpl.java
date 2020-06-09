package com.codegym.casestudy.service.impl;

import com.codegym.casestudy.model.Blog;
import com.codegym.casestudy.model.Category;
import com.codegym.casestudy.repository.BlogRepository;
import com.codegym.casestudy.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Page<Blog> findAll(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Optional<Blog> findById(Long id) {
        return blogRepository.findById(id);
    }

    @Override
    public void save(Blog blog) {
        blogRepository.save(blog);
    }

    @Override
    public void remove(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Page<Blog> findAllByTittle(String tittle, Pageable pageable) {
        return blogRepository.findAllByTittle(tittle,pageable);
    }

    @Override
    public Page<Blog> findBlogsByAccount_id(Long id,Pageable pageable) {
        return blogRepository.findBlogsByAccount_Id(id,pageable);
    }

    @Override
    public Page<Blog> findBlogsByCategory(Category category, Pageable pageable) {
        return blogRepository.findBlogsByCategory(category,pageable);
    }
}
