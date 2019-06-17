package com.ttulka.samples.doubletesting.web1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("indexControllerWeb1")
@RequestMapping("${doubletesting.path.web1:}")
public class IndexController {

    @GetMapping
    public String index() {
        return "web1/index";
    }
}
