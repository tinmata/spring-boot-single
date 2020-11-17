package com.teamzc.training.config.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * アクセス許可制御をカスタイマイズするクラスです。
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
    if (response.isCommitted()) {
      return;
    }

    response.setStatus(HttpServletResponse.SC_FORBIDDEN);

    DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    redirectStrategy.sendRedirect(request, response, "/access-denied");
  }
}
