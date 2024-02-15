package com.massonus.rccnavigator.service;

import com.massonus.rccnavigator.entity.Image;
import com.massonus.rccnavigator.entity.Product;
import com.massonus.rccnavigator.repo.CompanyRepo;
import com.massonus.rccnavigator.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepo productRepo;
    private final CompanyRepo companyRepo;

    @Autowired
    public ProductService(ProductRepo productRepo, CompanyRepo companyRepo) {
        this.productRepo = productRepo;
        this.companyRepo = companyRepo;
    }

    public void saveProduct(final Product validProduct, final Image image, final Long companyId) {
        Product product = new Product();
        product.setTitle(validProduct.getTitle());
        product.setImage(image);
        product.setPrice(validProduct.getPrice());
        product.setCompany(companyRepo.findCompanyById(companyId));

        productRepo.save(product);
    }

    public void saveProduct(final Product validProduct) {

        productRepo.save(validProduct);
    }

    public void editProduct(final Long id, final Product product) {
        Product savedProduct = productRepo.findProductById(id);

        savedProduct.setTitle(product.getTitle());
        savedProduct.setPrice(product.getPrice());

        productRepo.save(savedProduct);
    }

    public void deleteProduct(final Product product) {
        productRepo.delete(product);
    }

    public Product getProductById(final Long id) {
        return productRepo.findProductById(id);
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public List<Product> getAllProductsByCompanyId(final Long companyId) {
        return productRepo.findProductsByCompanyId(companyId);
    }

}
