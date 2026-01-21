package com.cryfirock.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cryfirock.product.entity.Product;

@Repository
public interface JpaProductRepository extends JpaRepository<Product, Long> {
}
