package com.asterixcode.bankapi.domain.model;

import jakarta.persistence.*;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customers")
@NoArgsConstructor
@Getter
@Setter
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customers_id_generator")
  @SequenceGenerator(name = "customers_id_generator", sequenceName = "customers_id_seq")
  private Long id;

  private String email;

  private String password;

  private String role;

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Customer customer)) return false;
    return Objects.equals(getEmail(), customer.getEmail());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getEmail());
  }

  public boolean isNew() {
    return getId() == null;
  }
}
