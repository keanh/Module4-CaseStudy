package com.codegym.casestudy.service;

import com.codegym.casestudy.model.Category;

import java.util.Optional;

public interface CategoryService {
    Iterable<Category> findAll();
    Optional<Category> findById(Long id);
    void save(Category category);
    void remove(Long id);
}
