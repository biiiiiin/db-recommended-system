package com.the_ajou.model.category;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "createdAt")
    private String createdAt;

    @Column(name = "updatedAt")
    private String updatedAt;

    @Column(name = "status")
    private char status;

    @Builder
    Category(String name, String createdAt, String updatedAt, char status){
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }
}
