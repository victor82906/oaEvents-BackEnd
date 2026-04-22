package com.vmr.oaevents.repository;

import com.vmr.oaevents.model.Validador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidadorRepository extends JpaRepository<Validador, Long> {
}
