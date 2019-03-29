package cz.net21.ttulka.myshop.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("cz.net21.ttulka.myshop")
public class MyShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyShopApplication.class, args);
    }
}
