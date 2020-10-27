package com.teamzc.training.controller;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class TestController {

  @Autowired
  private Environment environment;
  @Value("${ext.fuga}")
  private String fuga;
  @Value("${my.secret.password}")
  private String password;
  @Value("${my.uuid}")
  private UUID uuid;
  @Value("${my.int.value}")
  private Integer intValue;
  @Value("${my.int.range}")
  private Integer intRange;
  @Value("${my.int.less.than.ten}")
  private Integer intLessThanTen;
  @Value("${my.long.value}")
  private Long longValue;
  @Value("${my.long.range}")
  private Long longRange;

  @GetMapping("/get-ext-properties")
  public String getExtProperties(Model model) {

    log.info("外部ファイルからプロパティ値を取得します。");
    model.addAttribute("hoge", environment.getProperty("ext.hoge"));
    model.addAttribute("fuga", fuga);

    log.info("「RandomValuePropertySource」によるランダム値を取得します。");
    model.addAttribute("password", password);
    model.addAttribute("uuid", uuid);
    model.addAttribute("intValue", intValue);
    model.addAttribute("intRange", intRange);
    model.addAttribute("intLessThanTen", intLessThanTen);
    model.addAttribute("longValue", longValue);
    model.addAttribute("longRange", longRange);

    return "test";
  }

}
