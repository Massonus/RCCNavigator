package com.massonus.rccnavigator.controllers;

import com.massonus.rccnavigator.entity.Image;
import com.massonus.rccnavigator.entity.Product;
import com.massonus.rccnavigator.service.ImageService;
import com.massonus.rccnavigator.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ImageService imageService;

    @Autowired
    public ProductController(ProductService productService, ImageService imageService) {
        this.productService = productService;
        this.imageService = imageService;
    }

    @GetMapping
    public String getAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();

        model.addAttribute("products", products);

        return "products";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/new-product")
    public String newProduct(@RequestParam String title,
                             @RequestParam("file") MultipartFile multipartFile) {

        Image uploadImage;
        try {
            uploadImage = imageService.upload(multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        productService.saveProduct(title, uploadImage);

        return "redirect:/products";
    }
}
