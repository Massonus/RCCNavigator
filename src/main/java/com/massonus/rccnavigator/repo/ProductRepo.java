package com.massonus.rccnavigator.repo;

import com.massonus.rccnavigator.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    Product findProductById(Long id);

    List<Product> findProductsByCompanyId(Long companyId);

    List<Product> findProductsByTitleContainingIgnoreCase(String title);

    Product findProductByTitleAndCompanyId(String title, Long companyId);

}
