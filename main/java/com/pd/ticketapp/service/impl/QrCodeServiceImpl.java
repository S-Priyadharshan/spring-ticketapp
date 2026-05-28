package com.pd.ticketapp.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.pd.ticketapp.domain.entity.QrCode;
import com.pd.ticketapp.domain.entity.Ticket;
import com.pd.ticketapp.domain.enums.QrCodeStatus;
import com.pd.ticketapp.exception.QrCodeGenerationException;
import com.pd.ticketapp.exception.QrCodeNotFoundException;
import com.pd.ticketapp.repository.QrCodeRepository;
import com.pd.ticketapp.service.QrCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;


@RequiredArgsConstructor
@Slf4j
public class QrCodeServiceImpl implements QrCodeService {
    private static final int QR_HEIGHT = 300;
    private static final int QR_WIDTH = 300;

    private final QRCodeWriter qrCodeWriter;
    private final QrCodeRepository qrCodeRepository;

    @Override
    public QrCode generateQrCode(Ticket ticket){
        try{
            UUID uniqueId = UUID.randomUUID();
            String qrCodeImage = generateQrCodeImage(uniqueId);
            QrCode qrCode = QrCode.builder()
                    .id(uniqueId)
                    .status(QrCodeStatus.ACTIVE)
                    .value(qrCodeImage)
                    .ticket(ticket)
                    .build();
            return qrCodeRepository.saveAndFlush(qrCode);
        }catch(IOException | WriterException ex){
            throw new QrCodeGenerationException("Failed to generate QR code",ex);
        }
    }

    @Override
    public byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId) {
        QrCode qrCode = qrCodeRepository.findByTicketIdAndOwnerId(ticketId,userId)
                .orElseThrow(()->new QrCodeNotFoundException(String.format("QrCode with user id %s not found",userId)));

        try{
            return Base64.getDecoder().decode(qrCode.getValue());
        }catch(IllegalArgumentException ex){
            log.error("Invalid base64 QR Code for ticketId: {}",ticketId,ex);
            throw new QrCodeNotFoundException(String.format("QrCode with user id %s not found",userId));
        }

    }

    private String generateQrCodeImage(UUID uniqueId) throws WriterException,IOException{
        BitMatrix bitMatrix = qrCodeWriter.encode(
                uniqueId.toString(),
                BarcodeFormat.QR_CODE,
                QR_WIDTH,
                QR_HEIGHT
        );

        BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            ImageIO.write(qrCodeImage,"PNG",baos);
            byte[] imageBytes = baos.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        }
    }
}
