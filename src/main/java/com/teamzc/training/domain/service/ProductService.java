package com.teamzc.training.domain.service;

import com.teamzc.training.domain.entity.Product;
import com.teamzc.training.domain.reporitory.ProductReporitory;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class ProductService {

  @Autowired
  private ProductReporitory reporitory;

  public Product getOne(String productId) {
    // JpaRepository#getOne
    log.debug("Before: JpaRepository#getOne");
    Product product = reporitory.getOne(productId);
    log.debug("After: JpaRepository#getOne");
    return product;
  }

  public Product findById(String productId) {
    // CrudRepository#findById
    log.debug("Before: CrudRepository#findById");
    Optional<Product> product = reporitory.findById(productId);
    log.debug("Before: CrudRepository#findById");
    return product.orElseGet(Product::new);
  }

  public List<Product> findAllProducts() {
    return reporitory.findAll();
  }
}
