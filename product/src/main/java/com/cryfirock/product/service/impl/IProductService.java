package com.cryfirock.product.service.impl;

import com.cryfirock.product.entity.Product;
import com.cryfirock.product.type.ProductCategory;

public interface IProductService {
    void createProduct(Product product, ProductCategory category);
}
