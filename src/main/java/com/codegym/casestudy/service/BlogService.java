package com.codegym.casestudy.service;

import com.codegym.casestudy.model.Blog;
import com.codegym.casestudy.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BlogService {
    Page<Blog> findAll(Pageable pageable);
    Optional<Blog> findById(Long id);
    void save(Blog blog);
    void remove(Long id);
    Page<Blog> findAllByTittle(String tittle,Pageable pageable);
    Page<Blog> findBlogsByAccount_id(Long id,Pageable pageable);
    Page<Blog> findBlogsByCategory(Category category, Pageable pageable);
}
