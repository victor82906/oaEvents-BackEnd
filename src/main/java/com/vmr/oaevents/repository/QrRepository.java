package com.vmr.oaevents.repository;

import com.vmr.oaevents.model.Qr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QrRepository extends JpaRepository<Qr, Long> {
}
