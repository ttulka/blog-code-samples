package com.ttulka.samples.doubletesting.web2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("indexControllerWeb2")
@RequestMapping("${doubletesting.path.web2:}")
public class IndexController {

    @GetMapping
    public String index() {
        return "web2/index";
    }
}
