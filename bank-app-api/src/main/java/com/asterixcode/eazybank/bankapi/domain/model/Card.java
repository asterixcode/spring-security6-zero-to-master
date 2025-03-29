package com.asterixcode.eazybank.bankapi.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cards")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Card {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cards_id_seq")
  @SequenceGenerator(name = "cards_id_seq", sequenceName = "cards_id_seq", allocationSize = 1)
  @Column(name = "card_id")
  private Long cardId;

  @Column(name = "card_number")
  private String cardNumber;

  @Column(name = "card_type")
  private String cardType;

  @Column(name = "total_limit")
  private Long totalLimit;

  @Column(name = "amount_used")
  private Long amountUsed;

  @Column(name = "available_amount")
  private Long availableAmount;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "customer_id")
  private Long customerId;
}
