package com.asterixcode.eazybank.bankapi.application.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationEvents {

  private static final Logger log = LoggerFactory.getLogger(AuthorizationEvents.class);

  @EventListener
  public void onFailure(AuthorizationDeniedEvent<?> deniedEvent) {
    log.error(
        "Authorization failed for user : {} due to : {}",
        deniedEvent.getAuthentication().get().getName(),
        deniedEvent.getAuthorizationResult().toString());
  }
}
