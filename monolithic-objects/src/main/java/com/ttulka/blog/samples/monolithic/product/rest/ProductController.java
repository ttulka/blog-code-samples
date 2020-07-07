package com.ttulka.blog.samples.monolithic.product.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
class ProductController {

    @GetMapping
    public List<?> listProducts() {
        return null; // TODO
    }
}
