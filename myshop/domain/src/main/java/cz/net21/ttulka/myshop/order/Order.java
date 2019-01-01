package cz.net21.ttulka.myshop.order;

import java.util.Collection;
import java.util.HashSet;

import lombok.Value;

@Value
public class Order {

    private final long id;
    private final Customer customer;

    private final Collection<OrderItem> items = new HashSet<>();

    public static Order newOrder(String customerFullName) {
        return new Order(System.nanoTime(), new Customer(customerFullName));
    }

    public void addItem(long productId) {
        items.add(new OrderItem(productId));
    }

    public int itemsCount() {
        return items.size();
    }
}

@Value
class Customer {

    private final String fullName;
}

@Value
class OrderItem {

    private final long productId;
}
