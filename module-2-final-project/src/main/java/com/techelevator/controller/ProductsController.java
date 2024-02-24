package com.techelevator.controller;

import com.techelevator.dao.ProductDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.CartItem;
import com.techelevator.model.Product;
import com.techelevator.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@RestController
@PreAuthorize("permitAll")
@RequestMapping(value="/products")
public class ProductsController {

    @Autowired
    ProductDao productDao;

    @RequestMapping(value="", method = RequestMethod.GET)
    public List<Product> getProducts(
            @RequestParam(name="sku", defaultValue = "") String productSku,
            @RequestParam(name = "name", defaultValue = "") String productName) {

        if (!productSku.isEmpty()) {
            return productDao.getBySku(productSku);
        }
        if (!productName.isEmpty()) {
            return productDao.getByName(productName);
        }
        return productDao.getAll();
    }

    @GetMapping(value="/{id}")
    public Product getProductById(@PathVariable int id) {
        try {
            return productDao.getById(id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    // get by product id, get all products and get by product name (merge)
}
