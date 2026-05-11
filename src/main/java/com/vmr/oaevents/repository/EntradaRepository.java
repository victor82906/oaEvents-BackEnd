package com.vmr.oaevents.repository;

import com.vmr.oaevents.model.Entrada;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {

    Page<Entrada> findByCompradorId(Long compradorId, Pageable pageable);

    long countByZonaEvento_Evento_Id(Long eventoId);

    List<Entrada> findByCompradorIdAndZonaEvento_Evento_Id(Long compradorId, Long eventoId);

}
