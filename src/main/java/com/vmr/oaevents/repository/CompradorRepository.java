package com.vmr.oaevents.repository;

import com.vmr.oaevents.model.Comprador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompradorRepository extends JpaRepository<Comprador, Long> {

    boolean existByDni(String dni);

    boolean existByDniAndIdNot(String dni, Long id);

}
