package com.vmr.oaevents.service.impl;

import com.vmr.oaevents.model.Usuario;
import com.vmr.oaevents.model.dto.usuario.UsuarioOutputDto;
import com.vmr.oaevents.model.mapper.UsuarioMapper;
import com.vmr.oaevents.repository.UsuarioRepository;
import com.vmr.oaevents.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsuarioOutputDto findByEmail(String email){
        return usuarioMapper.toDto(repository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado.")));
    }

    @Override
    public List<Usuario> findAll() {
        return repository.findAll();
    }

    @Override
    public Usuario findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + id));
    }

    @Override
    public Usuario save(Usuario entity) {
        return repository.save(entity);
    }

    @Override
    public Usuario update(Long id, Usuario entity) {
        Usuario usuario = this.findById(id);
        entity.setId(id);
        entity.setContrasena(usuario.getContrasena());
        entity.setRol(usuario.getRol());
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.delete(this.findById(id));
    }
}
