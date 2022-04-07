package com.company.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "transaction")
public class TransactionEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(name="id",unique = true)
    private String id;

    @Column(name = "created_date")
    private LocalDateTime createdDate=LocalDateTime.now();

    @Column(name = "from_card_id",nullable = false)
    private Integer fromcardId;
    @ManyToOne
    @JoinColumn(name = "from_card_id",insertable = false,updatable = false)
    private CardEntity fromCard;

    @Column(name = "to_card_id",nullable = false)
    private Integer toCardId;
    @ManyToOne
    @JoinColumn(name = "to_card_id",insertable = false,updatable = false)
    private CardEntity toCard;

    @Column(name = "amount")
    private Long amount;

}
