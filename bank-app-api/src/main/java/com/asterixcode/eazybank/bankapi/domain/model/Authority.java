package com.asterixcode.eazybank.bankapi.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "authorities")
public class Authority {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authorities_id_seq")
  @SequenceGenerator(name = "authorities_id_seq", sequenceName = "authorities_id_seq", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  private String name;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;
}
