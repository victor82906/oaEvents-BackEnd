package com.vmr.oaevents.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Zona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String coordenadas;
    private String puertaEntrada;
    private boolean pista;

    @ManyToOne
    @JoinColumn(name = "recinto_id")
    private Recinto recinto;

    @OneToMany(mappedBy = "zona")
    private List<Localidad> localidades;

    @OneToMany(mappedBy = "zona")
    private List<ZonaEvento> zonaEventos;


}
