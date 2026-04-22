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
public class Localidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fila;
    private String numero;
    private double posX;
    private double posY;

    @ManyToOne
    @JoinColumn(name = "zona_id")
    private Zona zona;

    @OneToMany(mappedBy = "localidad")
    private List<Entrada> entradas;

}
