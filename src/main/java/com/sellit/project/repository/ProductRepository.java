package com.sellit.project.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.sellit.project.entities.Product;
import com.sellit.project.entities.User;

public interface ProductRepository extends JpaRepository<Product, User> {
    
    Product findByNameIgnoreCase(String name);

    @Query("SELECT p FROM Product p WHERE p.user.id = :user_id AND p.inCart = true")
    List<Product> findProductsInCartByUserId(@Param("user_id") Long user_id);

    Page<Product> findAll(Pageable pageable);
}
