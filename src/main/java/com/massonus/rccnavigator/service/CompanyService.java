package com.massonus.rccnavigator.service;

import com.massonus.rccnavigator.entity.Company;
import com.massonus.rccnavigator.entity.CompanyType;
import com.massonus.rccnavigator.entity.KitchenType;
import com.massonus.rccnavigator.entity.Product;
import com.massonus.rccnavigator.repo.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class CompanyService {
    private final CompanyRepo companyRepo;

    private final ProductService productService;

    @Autowired
    public CompanyService(CompanyRepo companyRepo, ProductService productService) {
        this.companyRepo = companyRepo;
        this.productService = productService;
    }

    public void saveCompany(final Company validCompany) {
        Company company = new Company();
        company.setTitle(validCompany.getTitle());
        company.setCompanyType(validCompany.getCompanyType());
        company.setKitchenType(validCompany.getKitchenType());
        company.setProducts(validCompany.getProducts());
        companyRepo.save(company);
    }

    public void editCompany(final Long id, final Company company) {
        Company savedCompany = companyRepo.findCompanyById(id);

        savedCompany.setTitle(company.getTitle());
        savedCompany.setCompanyType(company.getCompanyType());
        savedCompany.setKitchenType(company.getKitchenType());
        savedCompany.setProducts(company.getProducts());
        companyRepo.save(company);
    }

    public void deleteCompany(final Company company) {
        companyRepo.delete(company);
    }

    public Company getCompanyById(final Long id) {
        return companyRepo.findCompanyById(id);
    }

    public List<Company> getAllCompanies() {
        return companyRepo.findAll();
    }

    public Company createElementAuto() {
        Company company = new Company();

        company.setTitle("Test");
        company.setKitchenType(KitchenType.AMERICAN);
        company.setCompanyType(CompanyType.CAFFE);
        companyRepo.save(company);
        company.setProducts(createAndFillProductsListForCompany(company));

        return company;
    }

    public Set<Product> createAndFillProductsListForCompany(final Company company) {
        Set<Product> products = new HashSet<>();
        Random random = new Random();
        int lengthMas = random.nextInt(1, 3);
        for (int i = 0; i < lengthMas; i++) {
            Product product = new Product("Title " + i, 21 + i + "", company);
            productService.saveProduct(product);
            products.add(product);
        }
        return products;
    }
}