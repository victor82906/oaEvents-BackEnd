package com.vmr.oaevents.service.impl;

import com.vmr.oaevents.model.*;
import com.vmr.oaevents.model.dto.entrada.EntradaCompraInputDto;
import com.vmr.oaevents.repository.EntradaRepository;
import com.vmr.oaevents.security.AuthenticationFacade;
import com.vmr.oaevents.service.CompradorService;
import com.vmr.oaevents.service.EventoService;
import com.vmr.oaevents.service.LocalidadService;
import com.vmr.oaevents.service.ZonaEventoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntradaServiceImplTest {

    // 1. Mockeamos TODAS las dependencias del servicio de Entradas
    @Mock private EntradaRepository repository;
    @Mock private LocalidadService localidadService;
    @Mock private ZonaEventoService zonaEventoService;
    @Mock private CompradorService compradorService;
    @Mock private AuthenticationFacade authenticationFacade;
    @Mock private QrGeneratorService qrGeneratorService;
    @Mock private EventoService eventoService;
    @Mock private PdfService pdfService;

    // 2. Inyectamos los mocks en la clase a testear
    @InjectMocks
    private EntradaServiceImpl entradaService;

    @Test
    void testSave_UsuarioLogueado() {
        // Arrange
        Localidad localidadMock = new Localidad();
        localidadMock.setId(1L);

        Evento eventoMock = new Evento();
        eventoMock.setFecha(LocalDateTime.now().plusDays(10)); // Evento en 10 días

        ZonaEvento zonaEventoMock = new ZonaEvento();
        zonaEventoMock.setId(1L);
        zonaEventoMock.setPrecio(45.50);
        zonaEventoMock.setEvento(eventoMock);

        Entrada entrada = new Entrada();
        entrada.setLocalidad(localidadMock);
        entrada.setZonaEvento(zonaEventoMock);

        Comprador compradorAutenticado = new Comprador();
        compradorAutenticado.setNombre("Carlos Pérez");
        compradorAutenticado.setEmail("carlos@test.com");
        compradorAutenticado.setDni("12345678A");

        // Simulamos respuestas
        when(localidadService.findById(1L)).thenReturn(localidadMock);
        when(zonaEventoService.findById(1L)).thenReturn(zonaEventoMock);
        when(authenticationFacade.getAuthenticatedComprador()).thenReturn(compradorAutenticado);
        when(qrGeneratorService.generateQr(anyString())).thenReturn("uploads/qrs/codigo.png");
        when(repository.save(any(Entrada.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        Entrada resultado = entradaService.save(entrada, true);

        // Assert
        assertNotNull(resultado.getQr(), "Se debe haber generado un objeto QR");
        assertEquals("uploads/qrs/codigo.png", resultado.getQr().getFoto(), "La ruta del QR debe coincidir");
        assertEquals("Carlos Pérez", resultado.getNombreComprador(), "El nombre debe ser el del usuario logueado");
        assertEquals(45.50, resultado.getPrecio(), "El precio debe extraerse de la ZonaEvento");
        assertNotNull(resultado.getFechaCompra(), "La fecha de compra debe haberse registrado");

        verify(repository, times(1)).save(entrada);
    }

    @Test
    void testDescargarEntradaPdf() {
        // Arrange
        Long idEntrada = 1L;

        Evento eventoMock = new Evento();
        eventoMock.setTitulo("Concierto de Prueba");

        Zona zonaMock = new Zona();
        zonaMock.setPuertaEntrada("Puerta A");
        zonaMock.setNumero("1");
        zonaMock.setPista(true);

        ZonaEvento zonaEventoMock = new ZonaEvento();
        zonaEventoMock.setEvento(eventoMock);
        zonaEventoMock.setZona(zonaMock);

        Qr qrMock = new Qr();
        qrMock.setCodigo("uuid-secreto");
        qrMock.setFoto("qr.png");

        Entrada entradaMock = new Entrada();
        entradaMock.setId(idEntrada);
        entradaMock.setFechaCompra(LocalDateTime.now());
        entradaMock.setPrecio(30.0);
        entradaMock.setNombreComprador("Ana Gómez");
        entradaMock.setZonaEvento(zonaEventoMock);
        entradaMock.setQr(qrMock);

        // Ojo: Si tienes un método propio findById en EntradaServiceImpl,
        // y no puedes mockearlo directamente, necesitamos simular el repository
        // Asumiendo que this.findById llama a repository.findById:
        when(repository.findById(idEntrada)).thenReturn(Optional.of(entradaMock));

        byte[] pdfSimulado = new byte[]{1, 2, 3}; // Simulamos los bytes de un PDF
        when(pdfService.generarPdf(eq("entrada"), any(Map.class))).thenReturn(pdfSimulado);

        // Act
        byte[] resultado = entradaService.descargarEntradaPdf(idEntrada);

        // Assert
        assertNotNull(resultado);
        assertArrayEquals(pdfSimulado, resultado, "Los bytes devueltos deben coincidir con los generados por PdfService");

        verify(pdfService, times(1)).generarPdf(eq("entrada"), any(Map.class));
    }

    @Test
    void testProcesarPago_Invitado_Exito() {
        // Arrange
        EntradaCompraInputDto dto = new EntradaCompraInputDto();
        dto.setEvento_id(1L);
        dto.setZonaEvento_id(1L);
        dto.setLocalidad_ids(List.of(10L, 11L)); // Compra 2 entradas
        dto.setNombreComprador("Visitante");
        dto.setEmailComprador("visita@test.com");
        dto.setDniComprador("87654321B");

        ZonaEvento zonaEventoMock = new ZonaEvento();
        zonaEventoMock.setId(1L);
        zonaEventoMock.setPrecio(20.0);

        Evento eventoMock = new Evento();
        eventoMock.setFecha(LocalDateTime.now());
        zonaEventoMock.setEvento(eventoMock);

        // Simulamos que las localidades 10 y 11 están libres en la base de datos
        Localidad loc1 = new Localidad(); loc1.setId(10L);
        Localidad loc2 = new Localidad(); loc2.setId(11L);
        List<Localidad> localidadesLibres = List.of(loc1, loc2);

        when(zonaEventoService.findById(1L)).thenReturn(zonaEventoMock);
        when(localidadService.findLocalidadesLibresByZonaEventoId(1L)).thenReturn(localidadesLibres);

        // Simular dependencias del método interno this.save()
        when(localidadService.findById(anyLong())).thenReturn(new Localidad());
        when(qrGeneratorService.generateQr(anyString())).thenReturn("qr.png");
        when(repository.save(any(Entrada.class))).thenAnswer(i -> {
            Entrada e = i.getArgument(0);
            e.setId((long) (Math.random() * 100)); // Simulamos BD generando ID
            return e;
        });

        // Act
        List<Long> idsGenerados = entradaService.procesarPago(dto);

        // Assert
        assertEquals(2, idsGenerados.size(), "Deben haberse generado 2 IDs de entrada");
        verify(repository, times(2)).save(any(Entrada.class));
    }

    @Test
    void testProcesarPago_LocalidadNoDisponible_LanzaExcepcion() {
        // Arrange
        EntradaCompraInputDto dto = new EntradaCompraInputDto();
        dto.setEvento_id(1L);
        dto.setZonaEvento_id(1L);
        dto.setLocalidad_ids(List.of(10L)); // Quiere la localidad 10

        ZonaEvento zonaEventoMock = new ZonaEvento();
        zonaEventoMock.setId(1L);

        // Simulamos que la BD devuelve una lista vacía (ninguna libre)
        when(zonaEventoService.findById(1L)).thenReturn(zonaEventoMock);
        when(localidadService.findLocalidadesLibresByZonaEventoId(1L)).thenReturn(new ArrayList<>());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> entradaService.procesarPago(dto)
        );

        assertEquals("Alguna de las localidades seleccionada no esta libre", exception.getMessage());
        // Verificamos que NO se guarda nada si falla
        verify(repository, never()).save(any(Entrada.class));
    }
}