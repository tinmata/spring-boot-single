package com.teamzc.training.controller;

import com.teamzc.training.domain.utility.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Slf4j
@SuppressWarnings("unused")
public class HomeController {

  @GetMapping("/home")
  public String home() {
    return "home";
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/success")
  public String success(Model model) {
    model.addAttribute("authUserInfo", AuthUtils.getAuthUserDetails().getAuthUserInfo());
    return "success";
  }
}
