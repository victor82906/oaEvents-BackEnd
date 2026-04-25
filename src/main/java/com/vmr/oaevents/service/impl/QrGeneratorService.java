package com.vmr.oaevents.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class QrGeneratorService {

    @Value("${qrs.folder}")
    private String RUTA_CARPETA;

    public String generateQr(String codigo){
        try{
            Path ruta = FileSystems.getDefault().getPath(RUTA_CARPETA);
            if (!Files.exists(ruta)) {
                Files.createDirectories(ruta);
            }
            String nombreArchivo = codigo + ".png";
            Path rutaArchivo = FileSystems.getDefault().getPath(RUTA_CARPETA + nombreArchivo);

            // Dibujar el QR (300x300 píxeles)
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(codigo, BarcodeFormat.QR_CODE, 300, 300);

            // Guardar la imagen en el disco
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", rutaArchivo);

            return RUTA_CARPETA + nombreArchivo;
        }catch (Exception e){
            throw new RuntimeException("Error al generar codigo QR", e);
        }
    }

}
