package com.asterixcode.bankapi.application.controller;

import com.asterixcode.bankapi.domain.model.Card;
import com.asterixcode.bankapi.domain.repository.CardRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardsController {
  private final CardRepository cardsRepository;

  public CardsController(CardRepository cardsRepository) {
    this.cardsRepository = cardsRepository;
  }

  @GetMapping("/myCards")
  public List<Card> getCardDetails(@RequestParam long id) {
    return cardsRepository.findByCustomerId(id);
  }
}
