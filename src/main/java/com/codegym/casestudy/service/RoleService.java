package com.codegym.casestudy.service;

import com.codegym.casestudy.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> findAll();
    Optional<Role> findById(Long id);
    void save(Role role);
    void remove(Long id);
}
