package com.sellit.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sellit.project.entities.Product;
import com.sellit.project.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product findByNameIgnoreCase(String name){
        return productRepository.findByNameIgnoreCase(name);
    }

    @Transactional
    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}
