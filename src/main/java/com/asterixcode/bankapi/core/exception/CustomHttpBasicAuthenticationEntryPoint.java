package com.asterixcode.bankapi.core.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * This EntryPoint is invoked only on HTTP Basic Authentication and not on normal UI login flow.
 * This is useful for service-to-service communication where the client is not a browser. For normal
 * UI login flow, exception handling and redirection of the user is done by {@link
 * org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint}.
 */
public class CustomHttpBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {

  /**
   * Commences an authentication scheme.
   *
   * @param request that resulted in an {@code AuthenticationException}
   * @param response so that the user agent can begin authentication
   * @param authException that caused the invocation
   * @throws IOException if an input or output error occurs
   */
  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {
    LocalDateTime now = LocalDateTime.now();
    String message =
        (authException != null && authException.getMessage() != null)
            ? authException.getMessage()
            : "Unauthorized";
    String path = request.getRequestURI();
    response.setHeader("bank-error-reason", "Authentication failed");
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType("application/json;charset=UTF-8");
    String jsonResponse =
        String.format(
            """
        {
            "timestamp": "%s",
            "status": %d,
            "error": "%s",
            "message": "%s",
            "path": "%s"
        }
        """,
            now,
            HttpStatus.UNAUTHORIZED.value(),
            HttpStatus.UNAUTHORIZED.getReasonPhrase(),
            message,
            path);

    response.getWriter().write(jsonResponse);
  }
}
