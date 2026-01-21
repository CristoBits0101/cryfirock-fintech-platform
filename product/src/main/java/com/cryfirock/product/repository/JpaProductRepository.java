package com.cryfirock.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cryfirock.product.entity.Product;

public interface JpaProductRepository extends JpaRepository<Product, Long> {
}
