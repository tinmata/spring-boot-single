package com.teamzc.training.domain.reporitory;

import com.teamzc.training.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReporitory extends JpaRepository<Product, String> {

}
