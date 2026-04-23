package com.vmr.oaevents.repository;

import com.vmr.oaevents.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    boolean existByCif(String cif);

    boolean existByCifAndIdNot(String cif, Long id);

}
