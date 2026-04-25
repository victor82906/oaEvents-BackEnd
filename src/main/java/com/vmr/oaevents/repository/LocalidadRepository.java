package com.vmr.oaevents.repository;

import com.vmr.oaevents.model.Localidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalidadRepository extends JpaRepository<Localidad, Long> {

    List<Localidad> findByZonaId(Long zonaId);

    @Query("SELECT l FROM Localidad l WHERE l.zona.id = (SELECT ze.zona.id FROM ZonaEvento ze WHERE ze.id = :zonaEventoId) " +
           "AND l.id NOT IN (SELECT e.localidad.id FROM Entrada e WHERE e.zonaEvento.id = :zonaEventoId)")
    List<Localidad> findLocalidadesLibresByZonaEventoId(Long zonaEventoId);

}
