package com.company.controller;

import com.company.dto.TransactionDto;
import com.company.dto.TransactionFilterDto;
import com.company.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/transaction")
public class TransactionalController {
    @Autowired
    private TransactionService transactionService;
    @PostMapping("/")
    public ResponseEntity<TransactionDto> makeTransaction(
            @RequestBody TransactionDto dto){
        log.info("Transaction : {}",dto);
       return ResponseEntity.ok(transactionService.makeTransaction(dto));
    }
    @PostMapping("/filter")
    public ResponseEntity<Page<TransactionDto>> filter(Pageable pageable,
                                                       @RequestBody TransactionFilterDto dto){
        return null;
    }

    @GetMapping("/get_transaction_by_profile/{id}")
    public ResponseEntity<List<TransactionDto>> getTransactionByProfileId(
            @PathVariable("id") Integer profileId){
        List<TransactionDto> response=transactionService.getTransactionByProfileId(profileId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/All_credit/by_profile/{id}")
    public ResponseEntity<List<TransactionDto>> getProfileCreditList(@PathVariable("id")Integer profileId){
        List<TransactionDto> response=transactionService.getProfileCreditList(profileId);
        return ResponseEntity.ok(response);
    }
    @GetMapping("debit-all/by_profile/id={id}")
    public ResponseEntity<List<TransactionDto>> getProfileDebitList(@PathVariable("id")Integer id){
        List<TransactionDto> response=transactionService.getProfileDebitList(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("get_transaction/history/by_card/{id}")
    public ResponseEntity<List<TransactionDto>> getTransactionListByCardId(@PathVariable("id")Integer cardId){
       List<TransactionDto> response=transactionService.getTransactionListByCardId(cardId);
       return ResponseEntity.ok(response);
    }

    @GetMapping("/get_Credit_List/by_cardnumber/{number}")
    public ResponseEntity<List<TransactionDto>> getCardCreditList(@PathVariable("number") String number){
        List<TransactionDto> rs=transactionService.getCardCreditList(number);
        return ResponseEntity.ok(rs);
    }
    @GetMapping("get_debit/by_cardnumber/{number}")
    public ResponseEntity<List<TransactionDto>> getCardDebitList(@PathVariable("number")String number){
        List<TransactionDto> response=transactionService.getCardDebitList(number);
        return ResponseEntity.ok(response);
    }


}
