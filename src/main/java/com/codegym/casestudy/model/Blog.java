package com.codegym.casestudy.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table
@Data
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String tittle;
    
    @Column(length = 10000)
    private String content;

    @ManyToOne(targetEntity = Category.class)
    private Category category;

    @ManyToOne(targetEntity = PermissionView.class)
    private PermissionView permissionView;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public PermissionView getPermissionView() {
        return permissionView;
    }

    public void setPermissionView(PermissionView permissionView) {
        this.permissionView = permissionView;
    }
}
