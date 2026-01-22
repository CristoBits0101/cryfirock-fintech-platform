package com.cryfirock.product.service.api;

import org.springframework.stereotype.Service;

import com.cryfirock.product.entity.Product;
import com.cryfirock.product.service.impl.IProductService;

@Service
public class ProductServiceImpl implements IProductService {
    private final IProductService productService;

    public ProductServiceImpl(IProductService service) {
        productService = service;
    }

    @Override
    public void createProduct(Product product) {
    }
}
