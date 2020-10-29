package com.teamzc.training.domain.utility;

import com.teamzc.training.domain.entity.AuthUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {

  public static AuthUserDetails getAuthUserDetails() {
    if (null == SecurityContextHolder.getContext()) {
      throw new AuthenticatedUserNotFound();
    }

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (null == auth) {
      throw new AuthenticatedUserNotFound();
    }

    if (!(auth.getPrincipal() instanceof AuthUserDetails)) {
      throw new AuthenticatedUserNotFound();
    }
    return ((AuthUserDetails) auth.getPrincipal());
  }

  private static class AuthenticatedUserNotFound extends RuntimeException {

  }
}
