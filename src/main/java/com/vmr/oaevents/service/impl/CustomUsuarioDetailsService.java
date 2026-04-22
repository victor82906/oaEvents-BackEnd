package com.vmr.oaevents.service.impl;

import com.vmr.oaevents.model.Usuario;
import com.vmr.oaevents.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public Usuario loadUserByUsername(String email) {
        System.out.println("🔍 Buscando email en BD: '" + email + "'");
        System.out.println("🔍 Longitud: " + email.length());

        Optional<Usuario> user = usuarioRepository.findByEmail(email);

        if (user.isEmpty()) {
            System.out.println("❌ Email NO encontrado en BD");
            // Lista todos los emails para debug
            usuarioRepository.findAll().forEach(u ->
                    System.out.println("   BD tiene: '" + u.getEmail() + "'"));
        } else {
            System.out.println("✅ Email encontrado");
        }

        return user.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

}
