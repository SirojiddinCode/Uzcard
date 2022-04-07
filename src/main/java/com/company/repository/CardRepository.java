package com.company.repository;

import com.company.entity.CardEntity;
import com.company.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<CardEntity, Integer> {
    Optional<CardEntity> findByNumber(String number);

    boolean existsByNumber(String number);

    void deleteByNumber(String number);

    @Transactional
    @Modifying
    @Query("update CardEntity c  set c.balance=c.balance+:amount where c.number=:number")
    void updateBalance(@Param("number") String number,@Param("amount") Long amount);

    @Transactional
    @Query("select c.balance from CardEntity c where c.number=:number")
    Long getCardBalance(String number);
    List<CardEntity> findAllByProfile( ProfileEntity profile);
}
