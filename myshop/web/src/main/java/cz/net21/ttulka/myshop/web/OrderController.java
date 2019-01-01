package cz.net21.ttulka.myshop.web;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import cz.net21.ttulka.myshop.order.Order;
import cz.net21.ttulka.myshop.order.OrderService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
class OrderController {

    private final static String CART_COOKIE_NAME = "productsInCart";

    private final OrderService orderService;

    @GetMapping("/orders")
    public String list(Map<String, Object> model) {
        Collection<Order> orders = orderService.listAllOrders();
        model.put("orders", orders);

        return "orders";
    }

    @PostMapping("/order/new")
    public String placeOrder(String customer, @CookieValue(value = CART_COOKIE_NAME, defaultValue = "") String cookie,
                       HttpServletResponse response) {
        Set<Long> productsIds = Arrays.asList(cookie.split("&")).stream()
                .filter(StringUtils::hasLength)
                .map(Long::parseLong)
                .collect(Collectors.toSet());

        Order order = Order.newOrder(customer);
        productsIds.forEach(order::addItem);

        orderService.createOrder(order);

        removeCookie(CART_COOKIE_NAME, response);

        return "order-added";
    }

    private void removeCookie(String name, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, "");
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
