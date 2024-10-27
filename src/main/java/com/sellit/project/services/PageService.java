package com.sellit.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.sellit.project.entities.Product;
import com.sellit.project.repository.ProductRepository;

@Service
public class PageService {
    
    @Autowired
    public ProductRepository productRepository;

    public Page<Product> findAll(Pageable pageable){
       return  productRepository.findAll(pageable);
    }
}
