package com.vmr.oaevents.service.impl;

import com.vmr.oaevents.model.*;
import com.vmr.oaevents.model.dto.qr.CheckQrDto;
import com.vmr.oaevents.repository.QrRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QrServiceImplTest {

    @Mock
    private QrRepository repository;

    @InjectMocks
    private QrServiceImpl qrService;

    @Test
    void testFindByCodigo_Encontrado() {
        // Arrange
        String codigo = "UUID-SECRETO";
        Qr qrMock = new Qr();
        qrMock.setCodigo(codigo);

        when(repository.findByCodigo(codigo)).thenReturn(Optional.of(qrMock));

        // Act
        Qr resultado = qrService.findByCodigo(codigo);

        // Assert
        assertNotNull(resultado);
        assertEquals(codigo, resultado.getCodigo());
        verify(repository, times(1)).findByCodigo(codigo);
    }

    @Test
    void testFindByCodigo_NoEncontrado_LanzaExcepcion() {
        // Arrange
        String codigo = "FALSO-123";
        when(repository.findByCodigo(codigo)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> qrService.findByCodigo(codigo)
        );

        assertEquals("Código QR no encontrado", exception.getMessage());
    }

    @Test
    void testCheckQr_Exito_AccesoPermitido() {
        // Arrange
        CheckQrDto dto = new CheckQrDto();
        dto.setCodigo("VALIDO-123");
        dto.setEvento_id(1L);
        dto.setZonas_ids(List.of(10L, 11L)); // El portero controla estas dos puertas

        // Montamos el árbol de relaciones para que no dé NullPointerException
        Evento evento = new Evento();
        evento.setId(1L); // Coincide con el evento del portero

        Zona zona = new Zona();
        zona.setId(10L); // Coincide con una de las zonas del portero

        ZonaEvento zonaEvento = new ZonaEvento();
        zonaEvento.setEvento(evento);
        zonaEvento.setZona(zona);

        Entrada entrada = new Entrada();
        entrada.setZonaEvento(zonaEvento);

        Qr qrBase = new Qr();
        qrBase.setCodigo("VALIDO-123");
        qrBase.setUsado(false); // No ha entrado todavía
        qrBase.setEntrada(entrada);

        when(repository.findByCodigo("VALIDO-123")).thenReturn(Optional.of(qrBase));

        // Act
        boolean accesoPermitido = qrService.checkQr(dto);

        // Assert
        assertTrue(accesoPermitido, "El acceso debe ser concedido");
        assertTrue(qrBase.isUsado(), "El QR debe marcarse como usado");
        verify(repository, times(1)).save(qrBase);
    }

    @Test
    void testCheckQr_Error_YaUsado() {
        // Arrange
        CheckQrDto dto = new CheckQrDto();
        dto.setCodigo("USADO-123");

        Qr qrUsado = new Qr();
        qrUsado.setCodigo("USADO-123");
        qrUsado.setUsado(true); // ¡Alerta! Ya lo han usado antes

        when(repository.findByCodigo("USADO-123")).thenReturn(Optional.of(qrUsado));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> qrService.checkQr(dto)
        );

        assertEquals("El QR ya ha sido usado", exception.getMessage());
        verify(repository, never()).save(any(Qr.class)); // Verificamos que no se guarda nada
    }

    @Test
    void testCheckQr_Error_EventoEquivocado() {
        // Arrange
        CheckQrDto dto = new CheckQrDto();
        dto.setCodigo("VALIDO-123");
        dto.setEvento_id(2L); // El portero está en el evento 2

        Evento evento = new Evento();
        evento.setId(1L); // Pero la entrada es para el evento 1

        // AÑADIDO: Creamos una zona de relleno para que no salte el NullPointerException
        Zona zonaFalsa = new Zona();
        zonaFalsa.setId(99L);

        ZonaEvento zonaEvento = new ZonaEvento();
        zonaEvento.setEvento(evento);
        zonaEvento.setZona(zonaFalsa); // AÑADIDO: Se la asignamos

        Entrada entrada = new Entrada();
        entrada.setZonaEvento(zonaEvento);

        Qr qrBase = new Qr();
        qrBase.setUsado(false);
        qrBase.setEntrada(entrada);

        when(repository.findByCodigo("VALIDO-123")).thenReturn(Optional.of(qrBase));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> qrService.checkQr(dto)
        );

        assertEquals("El QR no pertenece a este evento", exception.getMessage());
    }

    @Test
    void testCheckQr_Error_PuertaEquivocada() {
        // Arrange
        CheckQrDto dto = new CheckQrDto();
        dto.setCodigo("VALIDO-123");
        dto.setEvento_id(1L);
        dto.setZonas_ids(List.of(10L)); // El portero controla la puerta 10

        Evento evento = new Evento();
        evento.setId(1L); // Mismo evento

        Zona zona = new Zona();
        zona.setId(99L); // Pero el usuario tiene entrada para la puerta 99

        ZonaEvento zonaEvento = new ZonaEvento();
        zonaEvento.setEvento(evento);
        zonaEvento.setZona(zona);

        Entrada entrada = new Entrada();
        entrada.setZonaEvento(zonaEvento);

        Qr qrBase = new Qr();
        qrBase.setUsado(false);
        qrBase.setEntrada(entrada);

        when(repository.findByCodigo("VALIDO-123")).thenReturn(Optional.of(qrBase));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> qrService.checkQr(dto)
        );

        assertEquals("El QR no tiene acceso por esta puerta", exception.getMessage());
    }
}