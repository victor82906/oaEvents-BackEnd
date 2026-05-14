package com.vmr.oaevents.service.impl;

import com.vmr.oaevents.model.Empresa;
import com.vmr.oaevents.model.Evento;
import com.vmr.oaevents.model.TipoEvento;
import com.vmr.oaevents.model.ZonaEvento;
import com.vmr.oaevents.repository.EventoRepository;
import com.vmr.oaevents.service.EmpresaService;
import com.vmr.oaevents.service.TipoEventoService;
import com.vmr.oaevents.service.ZonaEventoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventoServiceImplTest {

    // 1. Mockeamos TODAS las dependencias que usa EventoServiceImpl
    @Mock
    private EventoRepository repository;
    @Mock
    private TipoEventoService tipoEventoService;
    @Mock
    private EmpresaService empresaService;
    @Mock
    private ImagenService imagenService;
    @Mock
    private ZonaEventoService zonaEventoService;

    // 2. Inyectamos los mocks en el servicio que queremos probar
    @InjectMocks
    private EventoServiceImpl eventoService;

    @Test
    void testSave() {
        // Arrange (Preparar datos)
        TipoEvento tipoMock = new TipoEvento();
        tipoMock.setId(1L);

        Empresa empresaMock = new Empresa();
        empresaMock.setId(1L);

        Evento eventoEntrada = new Evento();
        eventoEntrada.setTipoEvento(tipoMock);
        eventoEntrada.setEmpresa(empresaMock);

        // Simulamos lo que devolverán los servicios externos
        when(tipoEventoService.findById(1L)).thenReturn(tipoMock);
        when(empresaService.findById(1L)).thenReturn(empresaMock);
        when(repository.save(any(Evento.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act (Ejecutar el método)
        Evento resultado = eventoService.save(eventoEntrada);

        // Assert (Comprobar resultados)
        assertFalse(resultado.isAceptado(), "El evento debe guardarse como NO aceptado por defecto");
        assertEquals(tipoMock, resultado.getTipoEvento());
        assertEquals(empresaMock, resultado.getEmpresa());

        // Verificamos que se llamó al repositorio para guardar
        verify(repository, times(1)).save(eventoEntrada);
    }

    @Test
    void testUpdate() {
        // Arrange
        Long idEvento = 1L;

        Evento eventoAntiguo = new Evento();
        eventoAntiguo.setId(idEvento);
        eventoAntiguo.setAceptado(true);
        eventoAntiguo.setFoto("ruta/foto/antigua.jpg");
        eventoAntiguo.setEmpresa(new Empresa());
        eventoAntiguo.setTipoEvento(new TipoEvento());

        Evento eventoNuevosDatos = new Evento();
        eventoNuevosDatos.setTitulo("Nuevo Titulo"); // Dato que queremos cambiar

        // Suponiendo que tu método this.findById(id) usa repository.findById internamente:
        when(repository.findById(idEvento)).thenReturn(Optional.of(eventoAntiguo));
        when(repository.save(any(Evento.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        Evento resultado = eventoService.update(idEvento, eventoNuevosDatos);

        // Assert
        assertEquals(idEvento, resultado.getId());
        assertTrue(resultado.isAceptado(), "Debe mantener el estado 'aceptado' del evento antiguo");
        assertEquals("ruta/foto/antigua.jpg", resultado.getFoto(), "Debe mantener la foto antigua");
        assertEquals("Nuevo Titulo", resultado.getTitulo(), "Debe actualizar los campos permitidos");

        verify(repository, times(1)).save(eventoNuevosDatos);
    }

    @Test
    void testAddFoto_ConFotoPrevia() {
        // Arrange
        Long idEvento = 1L;
        MultipartFile archivoMock = mock(MultipartFile.class);

        Evento evento = new Evento();
        evento.setId(idEvento);
        evento.setFoto("foto_vieja.jpg"); // Tiene foto previa

        when(repository.findById(idEvento)).thenReturn(Optional.of(evento));
        when(imagenService.guardarImagen(archivoMock)).thenReturn("nueva_foto.jpg");
        when(repository.save(any(Evento.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        Evento resultado = eventoService.addFoto(idEvento, archivoMock);

        // Assert
        assertEquals("nueva_foto.jpg", resultado.getFoto());
        // Verificamos que se eliminó la foto antigua
        verify(imagenService, times(1)).eliminarImagen("foto_vieja.jpg");
        // Verificamos que se guardó la nueva
        verify(imagenService, times(1)).guardarImagen(archivoMock);
        verify(repository, times(1)).save(evento);
    }

    @Test
    void testDeleteById() {
        // Arrange
        Long idEvento = 1L;

        ZonaEvento zona1 = new ZonaEvento();
        zona1.setId(10L);
        ZonaEvento zona2 = new ZonaEvento();
        zona2.setId(11L);

        Evento evento = new Evento();
        evento.setId(idEvento);
        evento.setFoto("foto_a_borrar.jpg");
        evento.setZonasEvento(List.of(zona1, zona2)); // Tiene 2 zonas

        when(repository.findById(idEvento)).thenReturn(Optional.of(evento));

        // Act
        eventoService.deleteById(idEvento);

        // Assert
        // 1. Verificamos que se borra la foto
        verify(imagenService, times(1)).eliminarImagen("foto_a_borrar.jpg");

        // 2. Verificamos que se borran las zonas iterando el stream
        verify(zonaEventoService, times(1)).deleteById(10L);
        verify(zonaEventoService, times(1)).deleteById(11L);

        // 3. Verificamos que se borra el evento
        verify(repository, times(1)).delete(evento);
    }
}