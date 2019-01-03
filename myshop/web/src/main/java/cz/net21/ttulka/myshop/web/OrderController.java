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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import cz.net21.ttulka.myshop.order.Order;
import cz.net21.ttulka.myshop.order.OrderService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
class OrderController {

    private final static String CART_COOKIE_NAME = "productsInCart";

    private final OrderService orderService;

    @GetMapping("/orders")
    public String list(String message, Map<String, Object> model) {
        Collection<Order> orders = orderService.listAllOrders();
        model.put("orders", orders);
        model.put("message", message);

        return "orders";
    }

    @PostMapping("/order/new")
    public RedirectView placeOrder(String customer, @CookieValue(value = CART_COOKIE_NAME, defaultValue = "") String cookie,
                                   HttpServletResponse response, RedirectAttributes redirectAttributes) {
        Set<Long> productsIds = Arrays.asList(cookie.split("&")).stream()
                .filter(StringUtils::hasLength)
                .map(Long::parseLong)
                .collect(Collectors.toSet());

        Order order = Order.newOrder(customer);
        productsIds.forEach(order::addItem);

        orderService.createOrder(order);

        removeCookie(CART_COOKIE_NAME, response);

        redirectAttributes.addAttribute("message", "order-placed-successfully");
        return new RedirectView("/orders");
    }

    private void removeCookie(String name, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, "");
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
