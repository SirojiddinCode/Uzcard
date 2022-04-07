package com.company.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDto {
    private String id;

    private LocalDateTime createdDate;

    private Integer fromcardId;

    private Integer toCardId;

    private Long amount;
}
