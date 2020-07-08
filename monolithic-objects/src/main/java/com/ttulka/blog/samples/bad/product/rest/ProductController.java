package com.ttulka.blog.samples.bad.product.rest;

import com.ttulka.blog.samples.bad.product.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
class ProductController {

    private final Products products;

    @GetMapping
    public Collection<Map<String, ?>> list() {
        return products.findAll().stream()
                .map(this::toData)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public Map<String, ?> find(@PathVariable Long id) {
        return toData(products.findById(new ProductId(id)));
    }

    @PutMapping("{id}/title")
    public void changeTitle(@PathVariable Long id, @RequestBody String title) {
        products.findById(new ProductId(id))
                .changeTitle(new Title(title));
    }

    @PutMapping("{id}/price")
    public void changeTitle(@PathVariable Long id, @RequestBody Double price) {
        products.findById(new ProductId(id))
                .changePrice(new Money(price));
    }

    private Map<String, ?> toData(Product p) {
        return Map.of(
                "id", p.id().value(),
                "title", p.title().value(),
                "price", p.price().value()
        );
    }
}
