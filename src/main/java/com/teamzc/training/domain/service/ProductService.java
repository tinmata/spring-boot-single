package com.teamzc.training.domain.service;

import com.teamzc.training.domain.entity.Product;
import com.teamzc.training.domain.reporitory.ProductReporitory;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("unused")
public class ProductService {

  @NonNull
  private final ProductReporitory productReporitory;

  public Product getOne(String productId) {
    // JpaRepository#getOne
    log.debug("Before: JpaRepository#getOne");
    Product product = productReporitory.getOne(productId);
    log.debug("After: JpaRepository#getOne");
    return product;
  }

  public Product findById(String productId) {
    // CrudRepository#findById
    log.debug("Before: CrudRepository#findById");
    Optional<Product> product = productReporitory.findById(productId);
    log.debug("Before: CrudRepository#findById");
    return product.orElseGet(Product::new);
  }

  public List<Product> findAllProducts() {
    return productReporitory.findAll();
  }
}
