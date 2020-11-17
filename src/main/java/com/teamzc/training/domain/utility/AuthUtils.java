package com.teamzc.training.domain.utility;

import com.teamzc.training.domain.entity.AuthenticatedUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * 認証されたユーザーの情報を取得するユーティリティクラスです。
 */
@Slf4j
@SuppressWarnings("Unused")
public class AuthUtils {

  public static AuthenticatedUser getAuthenticatedUser() {
    if (null == SecurityContextHolder.getContext()) {
      throw new AuthenticatedUserNotFound();
    }

    // 認証されたユーザーの情報を取得する
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (null == auth) {
      throw new AuthenticatedUserNotFound();
    }

    if (!(auth.getPrincipal() instanceof AuthenticatedUser)) {
      throw new AuthenticatedUserNotFound();
    }
    return ((AuthenticatedUser) auth.getPrincipal());
  }

  public static WebAuthenticationDetails getWebAuthenticationDetails() {
    if (null == SecurityContextHolder.getContext()) {
      throw new AuthenticatedUserNotFound();
    }

    // 認証されたユーザーの情報を取得する
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (null == auth) {
      throw new AuthenticatedUserNotFound();
    }

    if (!(auth.getDetails() instanceof WebAuthenticationDetails)) {
      throw new AuthenticatedUserNotFound();
    }
    return ((WebAuthenticationDetails) auth.getDetails());
  }

  private static class AuthenticatedUserNotFound extends RuntimeException {

  }
}
