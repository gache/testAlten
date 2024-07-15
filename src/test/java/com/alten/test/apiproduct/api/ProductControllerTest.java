package com.alten.test.apiproduct.api;


import com.alten.test.apiproduct.configuration.exception.NotFoundException;
import com.alten.test.apiproduct.model.Product;
import com.alten.test.apiproduct.service.ProductServiceInter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(MockitoJUnitRunner.class)
class ProductControllerTest {
    @Mock
    private MockMvc mockMvc;

    @Mock
    private ProductServiceInter productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }


    @Test
    void getAllProductsTest() throws Exception {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setCode("P12345");
        product1.setName("Smartphone");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setCode("P12346");
        product2.setName("Tablet");

        List<Product> productList = Arrays.asList(product1, product2);

        when(productService.getAllProducts()).thenReturn(productList);

        mockMvc.perform(get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].code").value("P12345"))
                .andExpect(jsonPath("$[0].name").value("Smartphone"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].code").value("P12346"))
                .andExpect(jsonPath("$[1].name").value("Tablet"));
    }

    @Test
    void getAllProductsNoContentTest() throws Exception {
        when(productService.getAllProducts()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void createProductTest() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setCode("P12345");
        product.setName("Smartphone");

        when(productService.createProduct(any(Product.class))).thenReturn(product);

        String productJson = "{\"code\":\"P12345\",\"name\":\"Smartphone\"}";


        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.code").value("P12345"))
                .andExpect(jsonPath("$.name").value("Smartphone"));
    }


    @Test
    void getProductByIdFoundTest() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setCode("P12345");
        product.setName("Smartphone");

        when(productService.getProductById(anyLong())).thenReturn(Optional.of(product));

        mockMvc.perform(get("/api/v1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.code").value("P12345"))
                .andExpect(jsonPath("$.name").value("Smartphone"));
    }

    @Test
    void getProductByIdNotFoundTest() throws Exception {
        when(productService.getProductById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateProductTest() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setCode("P12345");
        product.setName("Smartphone");

        when(productService.createProduct(any(Product.class))).thenReturn(product);

        String productJson = "{\"id\":1, \"code\":\"P12345\",\"name\":\"Smartphone\"}";

        mockMvc.perform(put("/api/v1/product/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.code").value("P12345"))
                .andExpect(jsonPath("$.name").value("Smartphone"));
    }

    @Test
    void deleteProductTest() throws Exception {
        Long productId = 1L;

        doNothing().when(productService).deleteProduct(productId);

        mockMvc.perform(delete("/api/v1/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Product deleted"));
    }

    @Test
    void deleteProduct_NotFoundTest() throws Exception {
        Long productId = 1L;

        doThrow(new NotFoundException("Product not found")).when(productService).deleteProduct(productId);

        mockMvc.perform(delete("/api/v1/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}