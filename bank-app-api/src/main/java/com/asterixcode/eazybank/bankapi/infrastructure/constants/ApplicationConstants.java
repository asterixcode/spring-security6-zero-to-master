package com.asterixcode.eazybank.bankapi.infrastructure.constants;

public final class ApplicationConstants {
  public static final String JWT_SECRET_KEY = "JWT_SECRET";
  public static final String JWT_SECRET_DEFAULT = "jaijweoaijAOJEAWPj21pKPAOKSPOaea";

  public static final String[] SWAGGER_WHITELIST = {
      "/v3/api-docs/**",
      "/swagger-ui/**",
      "/swagger-ui.html",
  };
}
