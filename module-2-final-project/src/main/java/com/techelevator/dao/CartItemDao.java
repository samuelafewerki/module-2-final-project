package com.techelevator.dao;

import com.techelevator.model.CartItem;

import java.util.List;

public interface CartItemDao {

    CartItem create (CartItem item);
    List<CartItem> getAll();
    CartItem getById(int id);
    CartItem update (CartItem item);
    void deleteById (int id);

    List<CartItem> getByCartItemsUserId(int id);
}
