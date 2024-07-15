package com.alten.test.apiproduct.service;

import com.alten.test.apiproduct.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductServiceInter {

    List<Product> getAllProducts();

    Optional<Product> getProductById(Long id);

    Product createProduct(Product product);

    void deleteProduct(Long id);
}
