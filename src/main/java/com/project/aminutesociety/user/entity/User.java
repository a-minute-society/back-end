package com.project.aminutesociety.user.entity;

import com.project.aminutesociety.user.dto.UserSignUpDto;
import com.project.aminutesociety.usercategory.entity.UserCategory;
import com.project.aminutesociety.util.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/*
setter 사용 금지
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String userName;
    private String userPw;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<UserCategory> userCategories = new ArrayList<>();

    public static User signUp(UserSignUpDto.Req req) { return req.toEntity(); }
}
