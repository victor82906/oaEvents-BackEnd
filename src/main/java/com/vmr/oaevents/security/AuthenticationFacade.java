package com.vmr.oaevents.security;

import com.vmr.oaevents.model.Comprador;
import com.vmr.oaevents.model.Usuario;
import com.vmr.oaevents.repository.CompradorRepository;
import com.vmr.oaevents.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationFacade {

    private final UsuarioRepository usuarioRepository;
    private final CompradorRepository compradorRepository;

    public Usuario getAuthenticatedUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado."));
    }

    public Comprador getAuthenticatedComprador() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return compradorRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Comprador no encontrado."));

    }

}
