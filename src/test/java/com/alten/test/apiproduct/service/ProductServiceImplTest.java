package com.alten.test.apiproduct.service;

import com.alten.test.apiproduct.configuration.exception.BadRequestException;
import com.alten.test.apiproduct.configuration.exception.NotFoundException;
import com.alten.test.apiproduct.model.Product;
import com.alten.test.apiproduct.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;


    @Test
    void getAllProductsTest() {
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


    @Test
    void CreateProductTest() {

        Product product = new Product();
        product.setId(1L);
        product.setCode("P12345");
        product.setName("Smartphone");

        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.createProduct(product);

        assertEquals(product, result);
        verify(productRepository).save(product);
    }

    @Test
    void createProductInvalidProductNameTest() {
        Product product = new Product();
        product.setId(1L);
        product.setCode("P12345");
        product.setName("");

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            productService.createProduct(product);
        });

        assertEquals("The name of Product is mandatory", exception.getMessage());
    }


    @Test
    void getProductByIdTest() {

        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setCode("P12345");
        product.setName("Smartphone");

        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getProductById(productId);

        assertEquals(product, result.get());

    }

    @Test
    void getProductByIdProductDoesNotExistTest() {
        Long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(false);

        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> {
            productService.getProductById(productId);
        });
        assertEquals("Product with id " + productId + " doesn't exists", badRequestException.getMessage());
    }


    @Test
    void deleteProductTest() {
        Long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(true);
        doNothing().when(productRepository).deleteById(productId);

        productService.deleteProduct(productId);

        verify(productRepository).existsById(productId);
        verify(productRepository).deleteById(productId);
    }

    @Test
    void deleteProductProductDoesNotExistTest() {
        Long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            productService.deleteProduct(productId);
        });

        assertEquals("Product with id " + productId + " doesn't exists", exception.getMessage());
    }


}