package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.CartItem;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class JdbcCartItemDao implements CartItemDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcCartItemDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public CartItem create(CartItem item) {
        String sql = "INSERT INTO cart_item (user_id, product_id, quantity) VALUES (?, ?, ?)";

        try {
            int newCartItemId = jdbcTemplate.queryForObject(
                    sql, int.class, item.getUserId(), item.getProductId(), item.getQuantity());

            return getById(newCartItemId);
        } catch (DataAccessException e) {
            throw new DaoException("Error creating cart item", e);
        }
    }

    @Override
    public List<CartItem> getAll() {
        List<CartItem> cartItems = new ArrayList<>();

        String sql = "SELECT * FROM cart_item";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        while (rowSet.next()) {
            CartItem cartItem = mapRowToCartItemDao(rowSet);
            cartItems.add(cartItem);
        }

        return cartItems;
    }

    @Override
    public CartItem getById(int id) {
        String sql = "SELECT * FROM cart_item WHERE cart_item_id = ?";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);

        if (rowSet.next()) {
            return mapRowToCartItemDao(rowSet);
        }

        return null;
    }

    @Override
    public CartItem update(CartItem item) {
        String sql = "UPDATE cart_item SET user_id = ?, product_id = ?, quantity = ? " +
                "WHERE cart_item_id = ?";

        try {
            jdbcTemplate.update(sql, item.getUserId(), item.getProductId(), item.getQuantity(), item.getCartItemId());
            return getById(item.getCartItemId());
        } catch (DataAccessException e) {
            throw new DaoException("Error updating cart item", e);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM cart_item WHERE cart_item_id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<CartItem> getByCartItemsUserId(int id) {
        List<CartItem> cartItems = new ArrayList<>();

        String sql = "SELECT * FROM cart_item WHERE user_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);

        while (rowSet.next()) {
            CartItem cartItem = mapRowToCartItemDao(rowSet);
            cartItems.add(cartItem);
        }

        return cartItems;
    }

    private CartItem mapRowToCartItemDao(SqlRowSet rowSet) {
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(rowSet.getInt("cart_item_id"));
        cartItem.setUserId(rowSet.getInt("user_id"));
        cartItem.setProductId(rowSet.getInt("product_id"));
        cartItem.setQuantity(rowSet.getInt("quantity"));
        return cartItem;
    }

} // get list of cart items for userId

