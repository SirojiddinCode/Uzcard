package com.company.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionFilterDto {
    private Integer profileId;
    private String cardNumber;
    private Integer cardId;
    private Long fromAmount;
    private Long toAmount;
    private LocalDate fromDate;
    private LocalDate toDate;
}
