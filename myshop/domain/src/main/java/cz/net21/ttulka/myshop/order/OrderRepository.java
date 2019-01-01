package cz.net21.ttulka.myshop.order;

import java.util.Collection;

interface OrderRepository {

    Collection<Order> listAll();

    Order getById(long id);

    void save(Order order);
}
