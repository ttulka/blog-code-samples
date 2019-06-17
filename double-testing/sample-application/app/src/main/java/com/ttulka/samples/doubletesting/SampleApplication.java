package com.ttulka.samples.doubletesting;

import com.ttulka.samples.doubletesting.web1.IndexController;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.Import;

@SpringBootConfiguration
@EnableAutoConfiguration
@Import(IndexController.class) // remove me!!!
public class SampleApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SampleApplication.class)
                .listeners(new ApplicationPidFileWriter())
                .properties(
                        "spring.jmx.enabled=false"

                ).run(args);
    }
}
