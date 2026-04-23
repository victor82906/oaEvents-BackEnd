package com.vmr.oaevents.service;

import com.vmr.oaevents.model.Usuario;
import com.vmr.oaevents.model.dto.usuario.CambiarContrasenaDto;
import com.vmr.oaevents.model.dto.usuario.UsuarioOutputDto;
import java.util.List;

public interface UsuarioService {
    UsuarioOutputDto findByEmail(String email);
    List<Usuario> findAll();
    Usuario findById(Long id);
    Usuario save(Usuario entity);
    Usuario update(Long id, Usuario entity);
    void deleteById(Long id);
    boolean existByEmail(String email);
    boolean existByEmail(String email, Long id);
    void cambiarContrasena(Long id, CambiarContrasenaDto cambiarContrasenaDto);
}
