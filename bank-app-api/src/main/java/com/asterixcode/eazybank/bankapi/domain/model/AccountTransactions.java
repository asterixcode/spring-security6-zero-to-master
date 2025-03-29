package com.asterixcode.eazybank.bankapi.domain.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "account_transactions")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AccountTransactions {

  @Id
  @Column(name = "transaction_id")
  private UUID transactionId;

  @Column(name = "account_number")
  private Long accountNumber;

  @Column(name = "customer_id")
  private Long customerId;

  @Column(name = "transaction_date")
  private LocalDate transactionDate;

  @Column(name = "transaction_summary")
  private String transactionSummary;

  @Column(name = "transaction_type")
  private String transactionType;

  @Column(name = "transaction_amount")
  private Long transactionAmount;

  @Column(name = "closing_balance")
  private Long closingBalance;

  @Column(name = "created_at")
  private Instant createdAt;

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof AccountTransactions accountTransactions)) return false;
    return transactionId.equals(accountTransactions.transactionId);
  }

  @Override
  public int hashCode() {
    return transactionId.hashCode();
  }
}
