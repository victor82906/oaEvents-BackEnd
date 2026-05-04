package com.vmr.oaevents.controller;

import com.vmr.oaevents.model.Empresa;
import com.vmr.oaevents.model.Usuario;
import com.vmr.oaevents.model.dto.usuario.LoginRequest;
import com.vmr.oaevents.model.dto.usuario.UsuarioOutputDto;
import com.vmr.oaevents.model.mapper.UsuarioMapper;
import com.vmr.oaevents.security.AuthenticationFacade;
import com.vmr.oaevents.security.JwtService;
import com.vmr.oaevents.security.TokenResponse;
import com.vmr.oaevents.service.EmpresaService;
import com.vmr.oaevents.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthenticationFacade authenticationFacade;
    private final UsuarioService usuarioService;
    private final EmpresaService empresaService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getContrasena()
                )
        );

        Usuario usuario = usuarioService.findByEmail(authentication.getName());

        if(usuario.getRol().getNombre().equals("EMPRESA")){
            Empresa empresa = empresaService.findById(usuario.getId());

            if(!empresa.isActiva()){
                throw new IllegalArgumentException("La empresa no está activa");
            }
        }

        if(usuario.getRol().getNombre().equals("VALIDADOR")){
            throw new IllegalArgumentException("Los validadores actualmente no tienen funcionalidad");
        }

        String token = jwtService.generateToken(usuario);
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @GetMapping("/who")
    public ResponseEntity<UsuarioOutputDto> who(){
        return ResponseEntity.ok(usuarioMapper.toDto(usuarioService.findByEmail(authenticationFacade.getAuthenticatedUsuario().getEmail())));
    }

}
