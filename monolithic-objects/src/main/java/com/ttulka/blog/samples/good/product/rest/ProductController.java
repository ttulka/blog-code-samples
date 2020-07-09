package com.ttulka.blog.samples.good.product.rest;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import com.ttulka.blog.samples.good.product.ChangeProductPrice;
import com.ttulka.blog.samples.good.product.ChangeProductTitle;
import com.ttulka.blog.samples.good.product.FindProduct;
import com.ttulka.blog.samples.good.product.ListAllProducts;
import com.ttulka.blog.samples.good.product.Money;
import com.ttulka.blog.samples.good.product.Product;
import com.ttulka.blog.samples.good.product.ProductId;
import com.ttulka.blog.samples.good.product.Title;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
class ProductController {

    private final ListAllProducts allProducts;
    private final FindProduct findProduct;
    private final ChangeProductTitle changeTitle;
    private final ChangeProductPrice changePrice;

    @GetMapping
    public Collection<Map<String, ?>> list() {
        return allProducts.list().stream()
                .map(this::toData)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public Map<String, ?> find(@PathVariable Long id) {
        return toData(findProduct.byId(new ProductId(id)));
    }

    @PutMapping("{id}/title")
    public void changeTitle(@PathVariable Long id, @RequestBody String title) {
        changeTitle.change(new ProductId(id), new Title(title));
    }

    @PutMapping("{id}/price")
    public void changePrice(@PathVariable Long id, @RequestBody Double price) {
        changePrice.change(new ProductId(id), new Money(price));
    }

    private Map<String, ?> toData(Product p) {
        return Map.of(
                "id", p.id().value(),
                "title", p.title().value(),
                "price", p.price().value()
        );
    }
}
