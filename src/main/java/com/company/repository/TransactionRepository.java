package com.company.repository;

import com.company.entity.CardEntity;
import com.company.entity.ProfileEntity;
import com.company.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity,String> {

    @Query("select t from TransactionEntity t where t.fromCard.profile=:profile or t.toCard.profile=:profile")
    List<TransactionEntity> findAllByProfile(@Param("profile") ProfileEntity profile );

    @Query("select t from TransactionEntity t where t.fromCard.profile=:profile order by t.createdDate desc ")
    List<TransactionEntity> findAllByProfileCreditList(ProfileEntity profile);

    @Query("select t from TransactionEntity t where t.toCard.profile=:profile order by t.createdDate desc ")
    List<TransactionEntity> findAllByProfileDebitList(ProfileEntity profile);

    @Query("select t from TransactionEntity  t where t.toCard=:card or t.fromCard=:card")
    List<TransactionEntity> findAllByCard(@Param("card") CardEntity card);

    @Query("select t from TransactionEntity t where t.fromCard=:card order by t.createdDate desc")
    List<TransactionEntity> findAllByCardCredit(@Param("card") CardEntity card);

    @Query("select t from TransactionEntity t where t.toCard=:card order by t.createdDate desc")
    List<TransactionEntity> findAllByCardDebit(@Param("card") CardEntity card);
}
