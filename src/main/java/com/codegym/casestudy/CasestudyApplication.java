package com.codegym.casestudy;

import com.codegym.casestudy.model.PermissionView;
import com.codegym.casestudy.service.BlogService;
import com.codegym.casestudy.service.CategoryService;
import com.codegym.casestudy.service.PermissionService;
import com.codegym.casestudy.service.ProvinceService;
import com.codegym.casestudy.service.impl.BlogServiceImpl;
import com.codegym.casestudy.service.impl.CategoryServiceImpl;
import com.codegym.casestudy.service.impl.PermissionServiceImpl;
import com.codegym.casestudy.service.impl.ProvinceServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class CasestudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(CasestudyApplication.class, args);
    }
}
