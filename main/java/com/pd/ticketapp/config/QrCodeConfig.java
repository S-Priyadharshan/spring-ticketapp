package com.pd.ticketapp.config;

import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.context.annotation.Bean;

public class QrCodeConfig {
    @Bean
    public QRCodeWriter qrCodeWriter(){
        return new QRCodeWriter();
    }

}
