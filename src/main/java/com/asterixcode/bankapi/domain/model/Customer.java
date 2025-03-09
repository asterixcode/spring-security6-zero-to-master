package com.asterixcode.bankapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "customers")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customers_id_generator")
  @SequenceGenerator(
      name = "customers_id_generator",
      sequenceName = "customers_id_seq",
      allocationSize = 1)
  @Column(name = "customer_id")
  private Long customerId;

  @Column(name = "name")
  private String name;

  @Column(name = "email", unique = true)
  private String email;

  @Column(name = "mobile_number")
  private String mobileNumber;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Column(name = "password")
  private String password;

  @Column(name = "role")
  private String role;

  @JsonIgnore
  @Column(name = "created_at")
  private Instant createdAt;

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
    return getCustomerId() == null;
  }
}
