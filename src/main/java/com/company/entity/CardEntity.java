package com.company.entity;

import com.company.enums.CardStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "card")
public class CardEntity extends BaseEntity {
    @Column(name = "number", nullable = false, unique = true)
    private String number;
    @Column(name = "exc_date")
    private String excDate;
    @Column(name = "balance")
    private Long balance;
    @Column(name = "password")
    private Integer password;
    @Column
    private String phone;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CardStatus status;

    @Column(name = "profile_id",nullable = false)
    private Integer profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id",insertable = false,updatable = false)
    private ProfileEntity profile;
}
