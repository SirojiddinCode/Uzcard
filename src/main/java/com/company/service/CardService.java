package com.company.service;

import com.company.dto.CardDTO;
import com.company.entity.CardEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.CardStatus;
import com.company.exception.BadRequestException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.CardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService {
    private final Logger log = LoggerFactory.getLogger(CardService.class);
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ProfileService profileService;

    public CardDTO create(CardDTO card) {
        if (cardRepository.existsByNumber(card.getNumber())) {
            log.warn("This card number allready exists:{}", card.getNumber());
            throw new BadRequestException("This card number allready exists");
        }
        CardEntity cardEntity = new CardEntity();
        cardEntity.setNumber(card.getNumber());
        cardEntity.setBalance(0L);
        cardEntity.setExcDate(card.getExcDate());
        cardEntity.setProfileId(card.getProfileId());
        cardEntity.setPassword(card.getPassword());
        cardEntity.setStatus(CardStatus.ACTIVE);
        cardEntity.setPhone(profileService.get(card.getProfileId()).getPhone());
        //cardEntity.setProfile(profile);
        cardRepository.save(cardEntity);
        card.setId(cardEntity.getId());
        card.setBalance(cardEntity.getBalance());
        card.setCreatedDate(cardEntity.getCreatedDate());
        card.setPhone(cardEntity.getPhone());
        return card;
    }

    @Transactional
    public void increaseBalance(String number, Long balance) {
        CardEntity card = get(number);
        if (balance>=0) {
            card.setBalance(card.getBalance() + balance);
            cardRepository.save(card);
        }else throw new BadRequestException("amount>=0");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void update_balance(String number, Long balance) {
        cardRepository.updateBalance(number, balance);
    }

    public void update(CardDTO dto) {
        if (dto.getId() == null) {
            throw new BadRequestException("Id can not be null");
        }
        CardEntity entity = get(dto.getId());
        entity.setPassword(dto.getPassword());
        entity.setNumber(dto.getNumber());
        entity.setExcDate(dto.getExcDate());
        cardRepository.save(entity);
    }

    public void delete(String number) {
        if (cardRepository.existsByNumber(number)) {
            log.info("Card deleted");
            cardRepository.deleteByNumber(number);
        } else {
            log.warn("Card not found");
            throw new ItemNotFoundException("Card not found");
        }
    }

    public List<CardDTO> getAll() {
        return cardRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public CardEntity get(Integer id) {
        return cardRepository.findById(id)
                .orElseThrow(() ->
                        new ItemNotFoundException("Card not found"));
    }

    public CardEntity get(String number) {
        return cardRepository.findByNumber(number)
                .orElseThrow(() ->
                        new ItemNotFoundException("Card not found"));
    }

    public List<CardEntity> getAllByProfileID(Integer profileId) {
        ProfileEntity profile = profileService.get(profileId);
        List<CardEntity> cardEntityList = cardRepository.findAllByProfile(profile);
        if (cardEntityList.isEmpty()) {
            throw new ItemNotFoundException("Cards of this profile are not available");
        }
        return cardEntityList;
    }

    public CardDTO getByNumber(String number) {
        return toDto(get(number));
    }

    public CardDTO toDto(CardEntity entity) {
        CardDTO dto = new CardDTO();
        dto.setId(entity.getId());
        dto.setBalance(entity.getBalance());
        dto.setNumber(entity.getNumber());
        dto.setPassword(entity.getPassword());
        dto.setProfileId(entity.getProfileId());
        dto.setExcDate(entity.getExcDate());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

}
