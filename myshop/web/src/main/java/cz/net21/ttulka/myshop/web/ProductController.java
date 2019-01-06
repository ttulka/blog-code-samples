package cz.net21.ttulka.myshop.web;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import cz.net21.ttulka.myshop.product.Product;
import cz.net21.ttulka.myshop.product.ProductService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public String list(String productName, Map<String, Object> model) {
        Collection<Product> products = StringUtils.isEmpty(productName)
                ? productService.listAllProducts()
                : productService.findProductByName(productName)
                        .map(Collections::singleton)
                        .orElseGet(Collections::emptySet);
        model.put("products", products);
        return "products";
    }
}
