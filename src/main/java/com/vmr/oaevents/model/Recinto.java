package com.vmr.oaevents.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recinto extends Usuario {

    private String ubicacion;
    private String mapa;

    @OneToMany(mappedBy = "recinto")
    private List<Zona> zonas;

}
