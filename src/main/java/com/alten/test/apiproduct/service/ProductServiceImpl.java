package com.alten.test.apiproduct.service;

import com.alten.test.apiproduct.model.Product;
import com.alten.test.apiproduct.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductServiceInter {


    public final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
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
