package com.asterixcode.bankapi.domain.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "loans")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Loan {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loans_id_generator")
  @SequenceGenerator(name = "loans_id_generator", sequenceName = "loans_id_seq", allocationSize = 1)
  @Column(name = "loan_number")
  private Long loanNumber;

  @Column(name = "customer_id")
  private Long customerId;

  @Column(name = "start_date")
  private LocalDate startDate;

  @Column(name = "loan_type")
  private String loanType;

  @Column(name = "total_loan")
  private Long totalLoan;

  @Column(name = "amount_paid")
  private Long amountPaid;

  @Column(name = "outstanding_amount")
  private Long outstandingAmount;

  @Column(name = "created_at")
  private Instant createdAt;

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Loan loan)) return false;
    return loanNumber.equals(loan.loanNumber);
  }

  @Override
  public int hashCode() {
    return loanNumber.hashCode();
  }
}
