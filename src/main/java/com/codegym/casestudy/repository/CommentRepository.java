package com.codegym.casestudy.repository;

import com.codegym.casestudy.model.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment,Long> {
}
