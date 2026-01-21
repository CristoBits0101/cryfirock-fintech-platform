package com.cryfirock.product.service.api;

import org.springframework.stereotype.Service;

import com.cryfirock.product.service.impl.IProductService;

@Service
public class ProductServiceImpl {
    private final IProductService productService;

    public ProductServiceImpl(IProductService service) {
        productService = service;
    }
}
