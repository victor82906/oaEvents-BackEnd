package com.vmr.oaevents.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImagenService {

    @Value("${eventos.fotos.folder}")
    private String RUTA_CARPETA;

    public String guardarImagen(MultipartFile archivo) {
        if (archivo.isEmpty()) {
            throw new IllegalArgumentException("El archivo de imagen está vacío");
        }
        
        try {
            Path ruta = Paths.get(RUTA_CARPETA);
            if (!Files.exists(ruta)) {
                Files.createDirectories(ruta);
            }

            // Generamos un nombre único para evitar colisiones
            String nombreOriginal = archivo.getOriginalFilename();
            String extension = nombreOriginal != null && nombreOriginal.contains(".") 
                    ? nombreOriginal.substring(nombreOriginal.lastIndexOf(".")) 
                    : ".jpg";
                    
            String nombreArchivo = UUID.randomUUID().toString() + extension;
            Path rutaArchivo = ruta.resolve(nombreArchivo);

            // Guardamos el archivo
            Files.write(rutaArchivo, archivo.getBytes());

            return rutaArchivo.toString().replace("\\", "/");
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }
    }

    public void eliminarImagen(String rutaRelativa) {
        if (rutaRelativa != null && !rutaRelativa.isEmpty()) {
            try {
                Path rutaArchivo = Paths.get(rutaRelativa);
                Files.deleteIfExists(rutaArchivo);
            } catch (IOException e) {
                // Solo logueamos el error pero no cortamos el flujo
                System.err.println("No se pudo eliminar la imagen anterior: " + e.getMessage());
            }
        }
    }
}
