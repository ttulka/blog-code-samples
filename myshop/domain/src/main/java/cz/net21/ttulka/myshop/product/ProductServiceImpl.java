package cz.net21.ttulka.myshop.product;

import java.util.Collection;
import java.util.Optional;

class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Collection<Product> listAllProducts() {
        return productRepository.listAll();
    }

    @Override
    public Optional<Product> findProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.getById(id);
    }
}
