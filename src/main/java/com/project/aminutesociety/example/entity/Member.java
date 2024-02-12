package com.project.aminutesociety.example.entity;

import com.project.aminutesociety.example.dto.CreateMemberDto;
import com.project.aminutesociety.util.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/*
setter 사용 금지
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MEMBERS")
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userId;
    private String userPw;

    public static Member createMember(CreateMemberDto.Req req) { return req.toEntity(); }
}
