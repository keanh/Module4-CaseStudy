package com.codegym.casestudy.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class PermissionView {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
}
