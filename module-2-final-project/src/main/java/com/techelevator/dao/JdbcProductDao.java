package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Product;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Service
public class JdbcProductDao implements ProductDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcProductDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Product create(Product item) {
        String sql = "INSERT INTO product (product_sku, name, description, price, image_name) VALUES (?, ?, ?, ?, ?)";

        try {
            jdbcTemplate.update(sql, item.getProductSku(), item.getName(), item.getDescription(), item.getPrice(), item.getImageName());
            return item;
        } catch (DataAccessException e) {
            throw new DaoException("Error creating product", e);
        }
    }

    @Override
    public List<Product> getAll() {
        String sql = "SELECT * FROM product";
        List<Product> products = new ArrayList<>();
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
        while (rowSet.next()) {
            products.add(mapRowToProductDao(rowSet));
        }
        return products;
    }

    @Override
    public Product getById(int id) {
        String sql = "SELECT * FROM product WHERE product_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
        if (rowSet.next()) {
            return mapRowToProductDao(rowSet);
        }
        return null;
    }

    @Override
    public Product update(Product item) {
        String sql = "UPDATE product SET product_sku = ?, name = ?, description = ?, price = ?, image_name = ? WHERE product_id = ?";
        try {
            jdbcTemplate.update(
                    sql,
                    item.getProductSku(),
                    item.getName(),
                    item.getDescription(),
                    item.getPrice(),
                    item.getImageName(),
                    item.getProductId()
            );
            return getById(item.getProductId());
        } catch (DataAccessException e) {
            throw new DaoException("Error unable to update product", e);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM product WHERE product_id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Product> getByName(String productName) {
        String sql = "SELECT * FROM product WHERE name = ?";
        List<Product> products = new ArrayList<>();
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, productName);
        while (rowSet.next()) {
            products.add(mapRowToProductDao(rowSet));
        }
        return products;
    }

    @Override
    public List<Product> getBySku(String productSku) {
        String sql = "SELECT * FROM product WHERE product_sku = ?";
        List<Product> products = new ArrayList<>();
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, productSku);
        while (rowSet.next()) {
            products.add(mapRowToProductDao(rowSet));
        }
        return products;
    }

    private Product mapRowToProductDao(SqlRowSet rowSet) {
        Product product = new Product();
        product.setProductId(rowSet.getInt("product_id"));
        product.setProductSku(rowSet.getString("product_sku"));
        product.setName(rowSet.getString("name"));
        product.setDescription(rowSet.getString("description"));
        product.setPrice(rowSet.getBigDecimal("price"));
        product.setImageName(rowSet.getString("image_name"));
        return product;
    }

    // join the cart item table or a subquery
}
