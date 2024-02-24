package com.techelevator.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private List<CartItem> cartItems = new ArrayList<>();
    private List<Product> productList = new ArrayList<>();
    private BigDecimal subtotal;
    private BigDecimal taxes;
    private BigDecimal total;

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTaxes() {
        return taxes;
    }

    public void setTaxes(BigDecimal taxes) {
        this.taxes = taxes;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setUser(User user) {
    }

    public void setCartItems(List<CartItem> items) {
        this.cartItems = items;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    // assign products to cart items
}
