package com.wefin.conversor_moedas.service.impl;

import com.wefin.conversor_moedas.dto.CreateUserDTO;
import com.wefin.conversor_moedas.exception.ConflictException;
import com.wefin.conversor_moedas.model.Usuario;
import com.wefin.conversor_moedas.repository.UsuarioRepository;
import com.wefin.conversor_moedas.service.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * The Class UserServiceImpl
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 09/05/2025
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario createUser(CreateUserDTO createUserDTO) {
        var userDB = usuarioRepository.findByUsername(createUserDTO.username());
        if (userDB.isPresent()) {
            throw new ConflictException("Username já esta em uso!");
        }
        var usuario =  new Usuario();
        usuario.setUsername(createUserDTO.username());
        usuario.setPasswordHash(passwordEncoder.encode(createUserDTO.password()));
        usuario.setEmail(createUserDTO.email());
        usuarioRepository.save(usuario);
        return usuario;
    }


    @Override
    public boolean existsByUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }
}
