package com.company.spec;

import com.company.entity.ProfileEntity;
import com.company.entity.TransactionEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TransactionSpecification {

    public static Specification<TransactionEntity>lessThanOrEqualTo(LocalDate todate){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"),LocalDateTime.of(todate, LocalTime.MAX));
    }
    public static Specification<TransactionEntity> greaterThanOrEqualTo(LocalDate fromDate){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"),LocalDateTime.of(fromDate,LocalTime.MIN));
    }
    public static Specification<TransactionEntity> equalsProfile(String field, ProfileEntity profile){
       return  (root, query, criteriaBuilder) ->
               criteriaBuilder.equal(root.get(field),profile);
    }

}
