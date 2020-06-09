package com.codegym.casestudy.repository;

import com.codegym.casestudy.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category,Long> {
}
