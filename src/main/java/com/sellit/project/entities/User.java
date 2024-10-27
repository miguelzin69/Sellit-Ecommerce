package com.sellit.project.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sellit.project.services.PixQrCodeFunctions;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "users")  // Avoid using spaces or special characters
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // It's better to use Long for consistency

    @Column(unique = true, nullable = false)
    @NonNull
    private String name;

    @Column(nullable = false)
    @NonNull
    private String password;

    @Column(nullable = false, unique = true)  // Email should be unique
    @NonNull
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @NonNull
    @Column(nullable = false)
    private String city;         // Cidade do recebedor

    @NonNull
    @Column(nullable = false)
    private String pixKey;       // Chave Pix (pode ser e-mail, CPF, telefone, etc.)

    @NonNull
    @Column(nullable = false)
    private String pixKeyType;   // Tipo da chave (CPF, CNPJ, e-mail, telefone, aleatória)
    
    @NonNull
    @Column(nullable = false)
    private String country;      // País do recebedor (ex: "BR")
    
    @NonNull
    @Column(nullable = true)
    private String transactionId; // ID de transação (opcional)

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Product> shoppingCart = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> productsForSale = new ArrayList<>();

    public User(String name, String password, String email, Role role){
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public static String createPixPayload(String pixKey, String pixKeyType, String name, String city, String transactionId, double amount) {
        String pixPayload = "00020126400014";
        pixPayload += "br.gov.bcb.pix";  // Domínio da chave Pix
        pixPayload += "01" + String.format("%02d", pixKey.length()) + pixKey; // Chave Pix
    
        // Categoria de código e valor
        pixPayload += "0203Pix";                // Descrição da categoria
        pixPayload += "52040000";               // Código da categoria
        pixPayload += "5303986";                // Código da moeda (BRL)
        
        // Valor da transação correto
        pixPayload += "5406" + "100.00"; // Valor da transação em centavos
    
        // País, nome e cidade do recebedor
        pixPayload += "5802BR";                 // País do recebedor
        pixPayload += "59" + String.format("%02d", name.length()) + name;  // Nome do recebedor
        pixPayload += "60" + String.format("%02d", city.length()) + city;  // Cidade do recebedor
    
        // ID de transação
        if (transactionId != null) {
            pixPayload += "62290525yDNN08Wsb2MvZtCTpFqn0qLLs"; // ID de transação
        } else {
            pixPayload += "62070503***";  // Informações adicionais (placeholder)
        }
    
        // Calcular CRC16
        String crc16 = PixQrCodeFunctions.calculateCRC16(pixPayload + "6304");
        pixPayload += "6304" + crc16;
    
        return pixPayload;
    }
    
    
}



