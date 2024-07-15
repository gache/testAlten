package com.alten.test.apiproduct.service;

import com.alten.test.apiproduct.model.Product;
import com.alten.test.apiproduct.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;


    @Test
    public void getAllProductsTest() {
        Product product = new Product();
        product.setId(1L);
        product.setCode("P12345");
        product.setName("Smartphone");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setCode("P12346");
        product2.setName("Tablet");

        List<Product> productList = Arrays.asList(product, product2);

        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.getAllProducts();

        assertEquals(2, result.size());
        assertEquals("Smartphone", result.get(0).getName());
        assertEquals("Tablet", result.get(1).getName());

    }

}