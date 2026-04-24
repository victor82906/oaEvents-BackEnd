package com.vmr.oaevents.repository;

import com.vmr.oaevents.model.Entrada;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {

    Page<Entrada> findByCompradorId(Long compradorId, Pageable pageable);

}
