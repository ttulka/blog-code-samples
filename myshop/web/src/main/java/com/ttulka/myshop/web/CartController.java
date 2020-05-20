package com.ttulka.myshop.web;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.ttulka.myshop.product.Product;
import com.ttulka.myshop.product.ProductService;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
class CartController {

    private final static String CART_COOKIE_NAME = "productsInCart";

    private final ProductService productService;

    @GetMapping("/cart")
    public String cart(Long productId, @CookieValue(value = CART_COOKIE_NAME, defaultValue = "") String cookie,
                       Map<String, Object> model, HttpServletResponse response) {
        String productIdsAsString = cookie + (productId != null ? "&" + productId : "");
        Set<Long> productsIds = Arrays.asList(productIdsAsString.split("&")).stream()
                .filter(StringUtils::hasLength)
                .map(Long::parseLong)
                .collect(Collectors.toSet());

        setCookie(CART_COOKIE_NAME, productIdsAsString, response);

        Set<Product> products = productsIds.stream()
                .map(productService::getProductById)
                .collect(Collectors.toSet());

        model.put("products", products);
        return "cart";
    }

    private void setCookie(String name, String value, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
