package com.vmr.oaevents.repository;

import com.vmr.oaevents.model.ZonaEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZonaEventoRepository extends JpaRepository<ZonaEvento, Long> {

    List<ZonaEvento> findByEventoId(Long eventoId);

}
