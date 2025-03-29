package com.asterixcode.eazybank.bankapi.domain.model;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "contact_messages")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Contact {

  @Id
  @Column(name = "contact_id")
  private String contactId;

  @Column(name = "contact_name")
  private String contactName;

  @Column(name = "contact_email")
  private String contactEmail;

  @Column(name = "subject")
  private String subject;

  @Column(name = "message")
  private String message;

  @Column(name = "created_at")
  private Instant createdAt;
}
