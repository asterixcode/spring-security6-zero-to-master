package com.asterixcode.bankapi.domain.repository;

import com.asterixcode.bankapi.domain.model.Contact;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends ListCrudRepository<Contact, String> {}
