package com.project.aminutesociety.usercategory.entity;

import com.project.aminutesociety.category.entity.Category;
import com.project.aminutesociety.user.entity.User;
import com.project.aminutesociety.util.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

@Entity
@Table(name = "USERS_CATEGORIES")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCategory extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "user_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

}
