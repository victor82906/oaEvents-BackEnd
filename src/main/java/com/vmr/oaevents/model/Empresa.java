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
public class Empresa extends Usuario {

    private String cif;
    private boolean activa;

    @OneToMany(mappedBy = "empresa")
    private List<Evento> eventos;

}
