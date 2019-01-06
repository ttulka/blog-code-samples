package cz.net21.ttulka.myshop.product;

import java.util.Collection;
import java.util.Optional;

interface ProductRepository {

    Collection<Product> listAll();

    Optional<Product> findByName(String name);

    Product getById(long id);

    void save(Product product);
}

