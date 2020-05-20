package com.ttulka.myshop.product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;

class ProductRepositoryJdbc implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Product> listAll() {
        return jdbcTemplate.query(
                "SELECT id, name, description, price FROM products",
                (rs, rowNum) -> productFromResultSet(rs));
    }

    @Override
    public Optional<Product> findByName(String name) {
        Collection<Product> results = jdbcTemplate.query(
                "SELECT id, name, description, price FROM products WHERE name LIKE ?",
                new Object[]{name},
                (rs, rowNum) -> productFromResultSet(rs));

        if (results != null && !results.isEmpty()) {
            return Optional.of(results.iterator().next());
        }
        return Optional.empty();
    }

    @Override
    public Product getById(long id) {
        return jdbcTemplate.queryForObject(
                "SELECT id, name, description, price FROM products WHERE id = ?",
                new Object[]{id},
                (rs, rowNum) -> productFromResultSet(rs));
    }

    private Product productFromResultSet(ResultSet rs) throws SQLException {
        return new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getBigDecimal("price"));
    }

    @Override
    public void save(Product product) {
        jdbcTemplate.update(
                "INSERT INTO products VALUES (?,?,?,?)",
                new Object[]{
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice()
                });
    }
}
