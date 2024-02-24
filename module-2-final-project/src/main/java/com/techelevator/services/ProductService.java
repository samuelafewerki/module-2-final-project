package com.techelevator.services;

import com.techelevator.dao.ProductDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getProducts(String productName, String productSku) {

        if (productName.isEmpty() && productSku.isEmpty()){
            return productDao.getAll();
        }
        if (!productSku.isEmpty())
        {
            return productDao.getBySku(productSku);
        }
        return productDao.getByName(productName);
    }

    public Product getProductById(int id) {
        Product product = productDao.getById(id);
        if (product == null){
            throw new DaoException("Product with id " + id + " not found.");
    }
        return product;
    }

    public void removeFromCart(int productId) {
    }

    public void clearCart() {
    }

    public void updateCartItem(Object product, Integer quantity) {
    }

    public void addToCart(Integer productId, Integer quantity) {
    }

    public List<Product> getProductsByName(String productName) {
        return null;
    }

    public List<Product> getProductsBySku(String productSku) {
        return null;
    }

    public List<Product> getAllProducts() {
        return null;
    }
}
