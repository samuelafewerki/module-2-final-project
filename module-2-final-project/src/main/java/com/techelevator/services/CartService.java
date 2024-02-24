package com.techelevator.services;

import com.techelevator.client.TaxRateClient;
import com.techelevator.dao.CartItemDao;
import com.techelevator.dao.ProductDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.CartItem;
import com.techelevator.model.Product;
import com.techelevator.model.ShoppingCart;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    CartItemDao cartItemDao;

    @Autowired
    UserDao userDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    TaxRateClient taxRateClient;

    public ShoppingCart getShoppingCartForUser(Principal userPrincipal) {


        String username = userPrincipal.getName();
        User user = userDao.getUserByUsername(username);
        List<CartItem> items = cartItemDao.getByCartItemsUserId(user.getId());
        List<Product> productList = new ArrayList<>();
        BigDecimal subPrice = BigDecimal.ZERO;
        int totalQuantity = 0;

        for (CartItem cartItem : items) {
            Product product = productDao.getById(cartItem.getProductId());
            productList.add(product);
            cartItem.setProduct(product);

            int quantity = cartItem.getQuantity();
            BigDecimal price = product.getPrice();

            BigDecimal itemSubtotal = price.multiply(BigDecimal.valueOf(quantity));
            subPrice = subPrice.add(itemSubtotal);

            totalQuantity += quantity;
        }

        BigDecimal taxRate = taxRateClient.getTaxRate(user.getStateCode());
        BigDecimal taxes = subPrice.multiply(taxRate);
        BigDecimal total = subPrice.add(taxes);

        // call tax rate subtotal x tax rate = tax amount then add tax amount to subtotal for the total and then set the total


        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCart.setCartItems(items);
        shoppingCart.setProductList(productList);
        shoppingCart.setSubtotal(subPrice.setScale(2, RoundingMode.HALF_DOWN));
        shoppingCart.setTaxes(taxes.setScale(2, RoundingMode.HALF_DOWN));
        shoppingCart.setTotal(total.setScale(2, RoundingMode.HALF_DOWN));

        return shoppingCart; // return list of cart items
    }

    public void addToCart(Principal userPrincipal, CartItem cartItem) {
        String username = userPrincipal.getName();
        User user = userDao.getUserByUsername(username);
        int itemProductId = cartItem.getProductId();
        // list of cart items by user id
        // loop through the list
        // if you match a product id from list of items
        // if above happens then get that item id
        List<CartItem> userCartItems = cartItemDao.getByCartItemsUserId(user.getId());
        CartItem existingCartItem = null; // cartitem getbycartitemid
        for (CartItem item : userCartItems) {
            if (item.getProductId() == itemProductId) {
    existingCartItem = item;
    break;
            }
        }
        if (existingCartItem != null) {
            int newQuantity = existingCartItem.getQuantity() + cartItem.getQuantity();
            existingCartItem.setQuantity(newQuantity);
            cartItemDao.update(existingCartItem);
        } else {
            cartItem.setUserId(user.getId());
            cartItemDao.create(cartItem);
        }
    }// If item is part of cart only update the quantity otherwise add to post request

    public void removeFromCart(Principal userPrincipal, int itemId) {
    }

    public void clearCart(Principal userPrincipal) {
    }
}
