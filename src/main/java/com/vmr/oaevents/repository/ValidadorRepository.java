package com.vmr.oaevents.repository;

import com.vmr.oaevents.model.Validador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidadorRepository extends JpaRepository<Validador, Long> {

    boolean existsByDni(String dni);

    boolean existsByDniAndIdNot(String dni, Long id);

    @Query("SELECT v FROM Validador v WHERE " +
           "LOWER(v.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
           "LOWER(v.email) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
           "LOWER(v.dni) LIKE LOWER(CONCAT('%', :termino, '%'))")
    Page<Validador> searchByNombreOrEmailOrDni(String termino, Pageable pageable);

}
