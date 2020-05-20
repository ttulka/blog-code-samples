package com.ttulka.myshop.order;

import java.util.Collection;

import org.springframework.jdbc.core.JdbcTemplate;

class OrderRepositoryJdbc implements OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrderRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Order> listAll() {
        Collection<Order> orders = jdbcTemplate.query(
                "SELECT id, customer FROM orders",
                (rs, rowNum) -> Order.constructOrder(
                        rs.getLong("id"),
                        rs.getString("customer")));

        orders.forEach(this::addItemsToOrder);

        return orders;
    }

    @Override
    public Order getById(long id) {
        Order order = jdbcTemplate.queryForObject(
                "SELECT id, customer FROM orders WHERE id = ?",
                new Object[]{id},
                (rs, rowNum) -> Order.constructOrder(
                        rs.getLong("id"),
                        rs.getString("customer")));

        addItemsToOrder(order);

        return order;
    }

    private void addItemsToOrder(Order order) {
        jdbcTemplate.query(
                "SELECT id FROM products JOIN orders_products ON id = product_id WHERE order_id = ?",
                new Object[]{order.getId()},
                (rs, rowNum) -> rs.getLong("id"))
                .forEach(order::addItem);
    }

    @Override
    public void save(Order order) {
        jdbcTemplate.update(
                "INSERT INTO orders VALUES (?,?)",
                new Object[]{
                        order.getId(),
                        order.getCustomer().getFullName()});

        order.getItems().forEach(
                item -> jdbcTemplate.update(
                        "INSERT INTO orders_products VALUES (?,?)",
                        new Object[]{order.getId(), item.getProductId()})
        );
    }
}
