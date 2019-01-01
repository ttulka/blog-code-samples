package cz.net21.ttulka.myshop.product;

import java.util.Collection;
import java.util.Optional;

public interface ProductService {

    Collection<Product> listAllProducts();

    Optional<Product> findProductByName(String name);

    Product getProductById(Long id);
}
