package com.codegym.casestudy.service;

import com.codegym.casestudy.model.PermissionView;

import java.util.List;
import java.util.Optional;

public interface PermissionService {
    List<PermissionView> findAll();
    Optional<PermissionView> findById(Long id);
    void save(PermissionView permissionView);
    void remove(Long id);
}
