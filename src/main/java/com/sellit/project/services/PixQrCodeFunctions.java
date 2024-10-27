package com.sellit.project.services;

public class PixQrCodeFunctions {

        public static String calculateCRC16(String payload) {
            int polynomial = 0x1021; 
            int crc = 0xFFFF;        
    
            byte[] bytes = payload.getBytes();
    
            for (byte b : bytes) {
                crc ^= (b << 8);  
                for (int i = 0; i < 8; i++) {
                    if ((crc & 0x8000) != 0) {
                        crc = (crc << 1) ^ polynomial;
                    } else {
                        crc <<= 1;
                    }
                }
            }
    
            crc &= 0xFFFF;  // Garante que o valor seja de 16 bits
    
            // Retorna o CRC16 no formato hexadecimal com 4 dígitos
            return String.format("%04X", crc);
        }
    }
