package com.teamzc.training.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
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

}
