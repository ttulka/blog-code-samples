package com.ttulka.samples.ecommerce.product.web;

import java.util.stream.Collectors;

import com.ttulka.samples.ecommerce.product.FindProducts;
import com.ttulka.samples.ecommerce.product.Promote;
import com.ttulka.samples.ecommerce.product.PutOnSale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(ProductWebController.URI)
@RequiredArgsConstructor
class ProductWebController {

    static final String URI = "/ui/products";

    private final @NonNull FindProducts findProducts;
    private final @NonNull PutOnSale putOnSale;
    private final @NonNull Promote promote;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("standards", findProducts.standards().stream()
                .map(p -> new ProductModel(p.id(), p.name(), p.price()))
                .collect(Collectors.toSet()));
        model.addAttribute("promos", findProducts.promos().stream()
                .map(p -> new ProductModel(p.id(), p.name(), p.price()))
                .collect(Collectors.toSet()));

        return "product";
    }

    @PostMapping("/sale")
    public String putOnSale(@NonNull PutOnSale.ToPutOnSale toPutOnSale) {
        putOnSale.product(toPutOnSale);

        return "redirect:" + URI;
    }

    @PostMapping("/promote")
    public String promote(@NonNull Promote.ToPromote toPromote) {
        promote.product(toPromote);

        return "redirect:" + URI;
    }

    @RequiredArgsConstructor
    static class ProductModel {

        public final @NonNull Long id;
        public final @NonNull String name;
        public final @NonNull Double price;
    }
}
