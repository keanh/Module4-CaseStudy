package com.codegym.casestudy.service;

import com.codegym.casestudy.model.Province;

import java.util.List;
import java.util.Optional;

public interface ProvinceService {
    List<Province> findAll();
    Optional<Province> findById(Long id);
    void save(Province province);
    void remove(Long id);
}
