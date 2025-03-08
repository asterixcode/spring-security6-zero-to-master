package com.asterixcode.bankapi.domain.repository;

import com.asterixcode.bankapi.domain.model.Card;
import java.util.List;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends ListCrudRepository<Card, Long> {
  List<Card> findByCustomerId(Long customerId);
}
