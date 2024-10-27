package com.sellit.project.controller;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.WriterException;
import com.sellit.project.entities.User;
import com.sellit.project.services.GeneratePixQRCodeService;

@RestController
public class PixController {
    private final GeneratePixQRCodeService generatePixQRCodeService;

    public PixController(GeneratePixQRCodeService generatePixQRCodeService){
        this.generatePixQRCodeService = generatePixQRCodeService;
    }

    @GetMapping("/generatePixQrCode")
    public ResponseEntity<byte[]> generatePixQRCode(@RequestParam String pixKey,
    @RequestParam String pixKeyType,
    @RequestParam String name,
    @RequestParam String city,
    @RequestParam String transactionId,
    @RequestParam double amount) throws WriterException, IOException{
        String pixPayload = User.createPixPayload(pixKey, pixKeyType, name, city, transactionId, amount);
        byte[] pixQrCodeImage = generatePixQRCodeService.generatePixQRCode(pixPayload);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, "image/png");

        System.out.println("Generated Pix Payload: " + pixPayload);


        return ResponseEntity.ok()
            .headers(httpHeaders)
            .body(pixQrCodeImage);
        
    }
}
