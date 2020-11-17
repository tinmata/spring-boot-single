package com.teamzc.training.controller;

import com.teamzc.training.domain.utility.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@SuppressWarnings("unused")
public class LoginController {

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/success")
  public String success(Model model) {
    log.info("[details]");
    log.info("  IP Address : {}", AuthUtils.getWebAuthenticationDetails().getRemoteAddress());
    log.info("  Session ID : {}", AuthUtils.getWebAuthenticationDetails().getSessionId());
    log.info("[Principal]");
    log.info("  username : {}", AuthUtils.getAuthenticatedUser().getUsername());
    log.info("  password : {}", AuthUtils.getAuthenticatedUser().getPassword());
    model.addAttribute("authUserInfo", AuthUtils.getAuthenticatedUser().getAuthUserInfo());
    return "success";
  }

  @GetMapping("/access-denied")
  public String accessDenied() {
    return "access-denied";
  }

  @GetMapping("/encode-password/{password}")
  @ResponseBody
  public String encodePassword(@PathVariable("password") String password) {
    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    return passwordEncoder.encode(password);
  }

}
