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
public class Comprador extends Usuario {

    private String dni;
    private String apellidos;

    @OneToMany(mappedBy = "comprador")
    private List<Entrada> entradas;

}
