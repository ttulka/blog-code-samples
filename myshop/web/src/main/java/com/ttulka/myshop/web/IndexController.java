package com.ttulka.myshop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
class IndexController {

    @GetMapping("/")
    public RedirectView index() {
        return new RedirectView("/products");
    }
}
