package com.sellit.project.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.sellit.project.dto.ProductRegisterDto;
import com.sellit.project.dto.ProductRequestDTO;
import com.sellit.project.entities.Product;
import com.sellit.project.repository.UserRepository;
import com.sellit.project.services.ProductService;
import com.sellit.project.services.ShoppingCartService;

@RestController
@ResponseBody
public class ProductController {

    private final ProductService productService;

    private final ShoppingCartService shoppingCartService;

    private final UserRepository userRepository;

    public ProductController(UserRepository userRepositoryImpl, ProductService productServiceImpl,
            ShoppingCartService shoppingCartServiceImpl) {
        this.productService = productServiceImpl;
        this.userRepository = userRepositoryImpl;
        this.shoppingCartService = shoppingCartServiceImpl;
    }

    @PostMapping("/registerProduct")
    public ResponseEntity<String> registerProduct(@RequestBody ProductRegisterDto productDto) {
        var user = userRepository.findByNameIgnoreCase(productDto.userName());

        Product product = new Product(productDto.name(), productDto.price(), productDto.useState());

        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        product.setUser(user);

        productService.saveProduct(product);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/buyProduct")
    public ResponseEntity<String> buyProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        var product = productService.findByNameIgnoreCase(productRequestDTO.productName());

        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }

        if (product.isInCart()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product is already in the cart.");
        }

        product.setInCart(true);
        product.getUser().getShoppingCart().add(product);
        productService.saveProduct(product);

        return ResponseEntity.ok("Product added to cart.");
    }

    @GetMapping("/getShoppingCart")
    public List<Product> getShoppingCartProducts(@RequestParam Long user_id) {
        return shoppingCartService.getShoppingCartByUserId(user_id);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productService.getProducts(pageable);
        return productPage.getContent();
    }
}
