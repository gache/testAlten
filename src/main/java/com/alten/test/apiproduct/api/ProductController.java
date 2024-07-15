package com.alten.test.apiproduct.api;

import com.alten.test.apiproduct.model.Product;
import com.alten.test.apiproduct.service.ProductServiceInter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final ProductServiceInter productService;

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductServiceInter productService) {
        this.productService = productService;
    }


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (products != null && products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        logger.info("find all products");
        return ResponseEntity.ok().body(products);
    }

    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product newProduct = productService.createProduct(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }


}
