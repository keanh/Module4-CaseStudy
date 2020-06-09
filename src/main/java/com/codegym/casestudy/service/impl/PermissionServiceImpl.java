package com.codegym.casestudy.service.impl;

import com.codegym.casestudy.model.PermissionView;
import com.codegym.casestudy.repository.PermissionViewRepository;
import com.codegym.casestudy.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionViewRepository permissionViewRepository;

    @Override
    public List<PermissionView> findAll() {
        return (List<PermissionView>) permissionViewRepository.findAll();
    }

    @Override
    public Optional<PermissionView> findById(Long id) {
        return permissionViewRepository.findById(id);
    }

    @Override
    public void save(PermissionView permissionView) {
        permissionViewRepository.save(permissionView);
    }

    @Override
    public void remove(Long id) {
        permissionViewRepository.deleteById(id);
    }
}
