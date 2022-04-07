package com.company.dto;

import com.company.enums.CardStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CardDTO {
    private Integer id;
    private String number;
    private String excDate;
    private Integer profileId;
    private Long balance;
    private Integer password;
    private String phone;
    private LocalDateTime createdDate;
    private CardStatus status;

}
