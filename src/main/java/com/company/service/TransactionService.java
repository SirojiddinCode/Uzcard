package com.company.service;

import com.company.dto.TransactionDto;
import com.company.dto.TransactionFilterDto;
import com.company.entity.CardEntity;
import com.company.entity.ProfileEntity;
import com.company.entity.TransactionEntity;
import com.company.enums.CardStatus;
import com.company.repository.CardRepository;
import com.company.repository.TransactionRepository;
import com.company.spec.TransactionSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CardService cardService;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ProfileService profileService;

    public TransactionDto makeTransaction(TransactionDto dto) {
        CardEntity fromCard = cardService.get(dto.getFromcardId()); // 1213
        CardEntity toCard = cardService.get(dto.getToCardId());
        return doTransaction(fromCard, toCard, dto.getAmount());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TransactionDto doTransaction(CardEntity fromCard, CardEntity toCard, Long amount) {
        Long balance = cardRepository.getCardBalance(fromCard.getNumber());
        if (fromCard.getStatus().equals(CardStatus.BLOCK)||toCard.getStatus().equals(CardStatus.BLOCK)){
            log.warn("Which is a card blocked:{},{}",fromCard,toCard);
            throw new RuntimeException("Which is a card blocked");
        }
        if (balance == null || balance < amount) {
            log.warn("Not enough balance.");
            throw new RuntimeException("Not enough balance.");
        }

        cardService.update_balance(fromCard.getNumber(), amount * -1);
        cardService.update_balance(toCard.getNumber(), amount);

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setFromCard(fromCard);
        transactionEntity.setToCard(toCard);
        transactionEntity.setAmount(amount);
        transactionEntity.setCreatedDate(LocalDateTime.now());
        transactionRepository.save(transactionEntity);
        return toDto(transactionEntity);
    }

    @Transactional(propagation = Propagation.NEVER)
    public boolean get() {
        transactionRepository.findAll();
        return true;
    }

    private TransactionDto toDto(TransactionEntity entity) {
        TransactionDto dto = new TransactionDto();
        dto.setAmount(entity.getAmount());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setId(entity.getId());
        dto.setFromcardId(entity.getFromcardId());
        dto.setToCardId(entity.getToCardId());
        return dto;
    }

    public List<TransactionDto> getTransactionByProfileId(Integer profileId) {
        ProfileEntity profile = profileService.get(profileId);
        return transactionRepository.findAllByProfile(profile)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<TransactionDto> getProfileCreditList(Integer profileId) {
        ProfileEntity profile = profileService.get(profileId);
        return transactionRepository.findAllByProfileCreditList(profile)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<TransactionDto> getProfileDebitList(Integer profileId) {
        ProfileEntity profile = profileService.get(profileId);
        return transactionRepository.findAllByProfileDebitList(profile)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<TransactionDto> getTransactionListByCardId(Integer cardId){
       CardEntity card=cardService.get(cardId);
       return transactionRepository.findAllByCard(card)
               .stream()
               .map(this::toDto)
               .collect(Collectors.toList());
    }

    public List<TransactionDto> getCardCreditList(String number){
        CardEntity card=cardService.get(number);
        return transactionRepository.findAllByCardCredit(card)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<TransactionDto> getCardDebitList(String number){
        CardEntity card=cardService.get(number);
        return transactionRepository.findAllByCardDebit(card)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<TransactionDto> filter( TransactionFilterDto filterDto){

        Specification<TransactionEntity> spec;
        if (filterDto.getToDate()!=null){
            spec=Specification.where(TransactionSpecification.lessThanOrEqualTo(filterDto.getToDate()));
        }else{
            spec=Specification.where(TransactionSpecification.lessThanOrEqualTo(LocalDate.now()));
        }

        if (filterDto.getProfileId()!=null){
            ProfileEntity profile=profileService.get(filterDto.getProfileId());
            spec=Specification.where(TransactionSpecification.equalsProfile("toCard.profile",profile))
                    .or(TransactionSpecification.equalsProfile("fromCard.profile",profile));
        }
        return null;
    }

}
