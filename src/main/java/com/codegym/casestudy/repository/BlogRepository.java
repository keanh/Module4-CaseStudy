package com.codegym.casestudy.repository;

import com.codegym.casestudy.model.Blog;
import com.codegym.casestudy.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BlogRepository extends PagingAndSortingRepository<Blog,Long> {
    Page<Blog> findAllByTittle(String tittle, Pageable pageable);
    Page<Blog> findBlogsByAccount_Id(Long id,Pageable pageable);
    Page<Blog> findBlogsByCategory(Category category, Pageable pageable);
}
