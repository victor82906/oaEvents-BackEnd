package com.vmr.oaevents.repository;

import com.vmr.oaevents.model.Localidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalidadRepository extends JpaRepository<Localidad, Long> {

    List<Localidad> findByZonaId(Long zonaId);

}
