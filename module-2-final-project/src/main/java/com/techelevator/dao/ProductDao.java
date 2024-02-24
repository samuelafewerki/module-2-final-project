package com.techelevator.dao;

import com.techelevator.model.Product;

import java.util.List;

public interface ProductDao {

    Product create (Product item);
    List<Product> getAll();
    Product getById(int id);
    Product update(Product item);
    void deleteById (int id);

    List<Product> getByName(String productName);

    List<Product> getBySku(String productSku);
}
