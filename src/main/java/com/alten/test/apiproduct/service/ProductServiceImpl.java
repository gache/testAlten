package com.alten.test.apiproduct.service;

import com.alten.test.apiproduct.model.Product;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductServiceInter {
    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return Optional.empty();
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {

    }
}
