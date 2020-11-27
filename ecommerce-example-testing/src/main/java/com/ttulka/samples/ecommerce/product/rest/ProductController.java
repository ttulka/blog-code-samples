package com.ttulka.samples.ecommerce.product.rest;

import java.util.Collection;
import java.util.stream.Collectors;

import com.ttulka.samples.ecommerce.product.FindProducts;
import com.ttulka.samples.ecommerce.product.Promote;
import com.ttulka.samples.ecommerce.product.PutOnSale;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
class ProductController {

    private final @NonNull FindProducts findProducts;
    private final @NonNull Promote promote;
    private final @NonNull PutOnSale putOnSale;

    @GetMapping("/standard")
    public Collection<ProductResponse> standard() {
        return findProducts.standards().stream()
                .map(p -> new ProductResponse(
                        p.id(), p.name(), p.price()))
                .collect(Collectors.toSet());
    }

    @PostMapping("/standard")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse putOnSale(@RequestBody @NonNull PutOnSale.ToPutOnSale toPutOnSale) {
        var product = putOnSale.product(toPutOnSale);
        return new ProductResponse(
                product.id(), product.name(), product.price());
    }

    @GetMapping("/promoted")
    public Collection<ProductResponse> promoted() {
        return findProducts.promos().stream()
                .map(p -> new ProductResponse(
                        p.id(), p.name(), p.price()))
                .collect(Collectors.toSet());
    }

    @PostMapping("/promoted")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse promote(@RequestBody @NonNull Promote.ToPromote toPromote) {
        var product = promote.product(toPromote);
        return new ProductResponse(
                product.id(), product.name(), product.price());
    }

    @RequiredArgsConstructor
    static class ProductResponse {

        public final @NonNull Long id;
        public final @NonNull String name;
        public final @NonNull Double price;
    }
}
