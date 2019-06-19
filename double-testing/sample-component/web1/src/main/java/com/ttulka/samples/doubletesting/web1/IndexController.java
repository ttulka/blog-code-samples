package com.ttulka.samples.doubletesting.web1;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("indexControllerWeb1")
@RequestMapping("${doubletesting.path.web1:}")
public class IndexController {

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public String index() {
        return "Web1";
    }
}
