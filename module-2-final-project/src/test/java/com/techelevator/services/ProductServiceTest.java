package com.techelevator.services;

import com.techelevator.dao.ProductDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {
    ProductDao mockDao;
    ProductService productService;

    @Before
    public void runThisBeforeEachTest() {
        mockDao = Mockito.mock(ProductDao.class);
        productService = new ProductService(mockDao);
    }

    public void getProductById_returnsProduct() {

        Product product = new Product();
        product.setProductId(5);
        product.setName("Apples");
        product.setDescription("Apple Description");
        product.setImageName("apple.png");
        product.setPrice(BigDecimal.ONE);
        Mockito.when(mockDao.getById(5));

        assertEquals(product, productService.getProductById(5));
    }

    @Test (expected = DaoException.class)
    public void getProductById_throwsExceptionIfProductDoesNotExist() {
        productService.getProductById(99);
    }

}