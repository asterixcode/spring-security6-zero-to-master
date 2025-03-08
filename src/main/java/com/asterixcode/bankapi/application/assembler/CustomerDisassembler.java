package com.asterixcode.bankapi.application.assembler;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import com.asterixcode.bankapi.application.model.RegisterCustomerRequest;
import com.asterixcode.bankapi.domain.model.Customer;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    builder = @Builder(disableBuilder = true),
    nullValuePropertyMappingStrategy = IGNORE,
    nullValueCheckStrategy = ALWAYS)
public interface CustomerDisassembler {

  @Mapping(target = "customerId", ignore = true)
  @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
  @Mapping(target = "id", ignore = true)
  Customer toDomainObject(RegisterCustomerRequest customer);

}
