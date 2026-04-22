package com.vmr.oaevents.repository;

import com.vmr.oaevents.model.Recinto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecintoRepository extends JpaRepository<Recinto, Long> {
}
