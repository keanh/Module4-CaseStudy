package com.codegym.casestudy.service;

import com.codegym.casestudy.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<Comment> findAll();
    Optional<Comment> findById(Long id);
    void save(Comment comment);
    void remove(Long id);
}
