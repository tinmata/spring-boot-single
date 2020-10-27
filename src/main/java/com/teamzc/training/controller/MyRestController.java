package com.teamzc.training.controller;

import com.teamzc.training.domain.entity.Product;
import com.teamzc.training.domain.service.ProductService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@Slf4j
public class MyRestController {

  @Autowired
  private ProductService productService;

  @GetMapping
  @ResponseBody
  public List<Product> getAllProducts() {
    log.info("全件検索処理を行います。");
    return productService.findAllProducts();
  }

  @GetMapping("{id}")
  @ResponseBody
  public Product getProduct(@PathVariable("id") String productId) {
    log.info("個別検索処理を行います。条件：「ProductId」、値：「{}」", productId);
    return productService.findById(productId);
  }
}
