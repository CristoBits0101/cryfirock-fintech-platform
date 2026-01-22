package com.cryfirock.product.service.impl;

import com.cryfirock.product.entity.Product;
import com.cryfirock.product.type.Category;

public interface IProductService {
    void createProduct(Product product, Category category);
}
