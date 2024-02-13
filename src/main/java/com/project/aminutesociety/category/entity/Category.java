package com.project.aminutesociety.category.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.aminutesociety.usercategory.entity.UserCategory;
import com.project.aminutesociety.util.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CATEGORIES")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id; // 자동 생성 PK

    @Column(name = "category_name")
    private String name; // 카테고리명

    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<UserCategory> userCategories = new ArrayList<>();
}
