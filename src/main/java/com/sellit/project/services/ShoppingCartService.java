package com.sellit.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sellit.project.entities.Product;
import com.sellit.project.repository.ProductRepository;

@Service
public class ShoppingCartService {
    
    @Autowired 
    ProductRepository productRepository;

    public List<Product> getShoppingCartByUserId(Long user_id){
        return productRepository.findProductsInCartByUserId(user_id);
    }
    
}
