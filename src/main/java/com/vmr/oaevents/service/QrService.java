package com.vmr.oaevents.service;

import com.vmr.oaevents.model.Qr;
import java.util.List;

public interface QrService {
    List<Qr> findAll();
    Qr findById(Long id);
    Qr save(Qr entity);
    Qr update(Long id, Qr entity);
    void deleteById(Long id);
}
