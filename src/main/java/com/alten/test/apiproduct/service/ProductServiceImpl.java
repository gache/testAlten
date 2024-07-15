package com.alten.test.apiproduct.service;

import com.alten.test.apiproduct.configuration.exception.BadRequestException;
import com.alten.test.apiproduct.configuration.exception.NotFoundException;
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
        if (!productRepository.existsById(id)) {
            throw new BadRequestException("Product with id " + id + " doesn't exists");
        }
        return productRepository.findById(id);
    }

    @Override
    public Product createProduct(Product product) {
        if (product.getName().isEmpty()) {
            throw new BadRequestException("The name of Product is mandatory");
        }
        return productRepository.save(product);
    }


    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("Product with id " + id + " doesn't exists");
        }
        productRepository.deleteById(id);
    }
}
