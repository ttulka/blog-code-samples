package com.ttulka.myshop.order;

import java.util.Collection;

class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public Collection<Order> listAllOrders() {
        return orderRepository.listAll();
    }

    @Override
    public void createOrder(Order order) {
        if (order.getItems().isEmpty()) {
            throw new IllegalStateException("Order must contain products!");
        }
        orderRepository.save(order);
    }
}
