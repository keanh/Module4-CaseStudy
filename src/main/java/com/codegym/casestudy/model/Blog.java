package com.codegym.casestudy.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table
@Data
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String tittle;
    
    @Column(length = 10000)
    private String content;

    @ManyToOne(targetEntity = Category.class)
    private Category category;

    @ManyToOne(targetEntity = PermissionView.class)
    private PermissionView permissionView;
}
