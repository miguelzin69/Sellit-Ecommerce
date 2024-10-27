package com.sellit.project.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.sellit.project.dto.UserRegisterDto;
import com.sellit.project.entities.Product;
import com.sellit.project.entities.User;
import com.sellit.project.repository.UserRepository;

@RestController
@ResponseBody
public class UserController {

    @Autowired
    UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    private ResponseEntity<Void> register(@RequestBody UserRegisterDto userDto){
        User user = new User(userDto.name(), userDto.password(), userDto.email(), userDto.role());
        userRepository.save(user);



        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile")
    private String showUserProfile(@Param(value = "name") String name){
        var user = userRepository.findByNameIgnoreCase(name);

        if(user == null){
            return "User is null";
        }

        if (user.getShoppingCart() == null) {
            return "Shopping cart is empty.";
        }

        Double totalPrice = user.getShoppingCart().stream()
            .map(Product::getPrice) // Get the price of each product
            .filter(Objects::nonNull) // Filter out null prices
            .reduce(0.0, Double::sum); // Sum the prices
            
        return "This is your profile:\n" +
        "Name: " + user.getName() + "\n" +
        "Email: " + user.getEmail() + "\n" +
        "Total amount in purchases R$ " + totalPrice;
    }
}
