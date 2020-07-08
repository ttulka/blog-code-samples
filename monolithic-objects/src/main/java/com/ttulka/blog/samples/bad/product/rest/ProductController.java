package com.ttulka.blog.samples.bad.product.rest;

import com.ttulka.blog.samples.bad.product.Product;
import com.ttulka.blog.samples.bad.product.ProductId;
import com.ttulka.blog.samples.bad.product.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
class ProductController {

    private final Products products;

    @GetMapping
    public Collection<Map<String, ?>> listProducts() {
        return products.findAll().stream()
                .map(this::toData)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public Map<String, ?> findProduct(Long id) {
        return toData(products.findById(new ProductId(id)));
    }

    private Map<String, ?> toData(Product p) {
        return Map.of(
                "id", p.id().value(),
                "title", p.title().value(),
                "price", p.price().value()
        );
    }
}
