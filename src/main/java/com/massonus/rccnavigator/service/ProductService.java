package com.massonus.rccnavigator.service;

import com.massonus.rccnavigator.entity.Image;
import com.massonus.rccnavigator.entity.Product;
import com.massonus.rccnavigator.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepo productRepo;

    private final UserService userService;

    @Autowired
    public ProductService(ProductRepo productRepo, UserService userService) {
        this.productRepo = productRepo;
        this.userService = userService;
    }

    public void saveProduct(final Product validProduct, final Image image) {
        Product product = new Product();
        product.setTitle(validProduct.getTitle());
        product.setImage(image);
        product.setPrice(validProduct.getPrice());
        productRepo.save(product);
    }

    public void editProduct(final Long id, final Product product) {
        Product savedProduct = productRepo.findProductById(id);

        savedProduct.setTitle(product.getTitle());
        savedProduct.setPrice(product.getPrice());

        productRepo.save(savedProduct);
    }

    public Product getProductById(final Long id) {
        return productRepo.findProductById(id);
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

}