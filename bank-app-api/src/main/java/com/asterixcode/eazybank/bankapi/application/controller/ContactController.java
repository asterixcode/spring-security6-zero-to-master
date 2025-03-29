package com.asterixcode.eazybank.bankapi.application.controller;

import com.asterixcode.eazybank.bankapi.domain.model.Contact;
import com.asterixcode.eazybank.bankapi.domain.repository.ContactRepository;
import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContactController {
  private final ContactRepository contactRepository;

  public ContactController(ContactRepository contactRepository) {
    this.contactRepository = contactRepository;
  }

  @PostMapping("/contact")
  public Contact saveContactInquiryDetails(@RequestBody Contact contact) {
    contact.setContactId(getServiceReqNumber());
    contact.setCreatedAt(Instant.now());
    return contactRepository.save(contact);
  }

  public String getServiceReqNumber() {
    int ranNum = ThreadLocalRandom.current().nextInt(999990000) + 10000;
    return String.format("SR%09d", ranNum);
  }
}
