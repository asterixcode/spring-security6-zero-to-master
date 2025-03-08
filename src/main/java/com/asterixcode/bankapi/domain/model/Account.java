package com.asterixcode.bankapi.domain.model;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Account {

  @Id private Long accountNumber;

  @Column(name = "account_type")
  private String accountType;

  @Column(name = "branch_address")
  private String branchAddress;

  @Column(name = "created_at")
  private Instant createdAt;

  @Column(name = "customer_id")
  private Long customerId;

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Account account)) return false;
    return accountNumber.equals(account.accountNumber);
  }

  @Override
  public int hashCode() {
    return accountNumber.hashCode();
  }

  public boolean isNew() {
    return accountNumber == null;
  }
}
