package com.wefin.conversor_moedas.service.impl;

import com.wefin.conversor_moedas.dto.LoginRequestRecordDTO;
import com.wefin.conversor_moedas.dto.LoginResponseRecordDTO;
import com.wefin.conversor_moedas.exception.LoginException;
import com.wefin.conversor_moedas.repository.UsuarioRepository;
import com.wefin.conversor_moedas.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import java.time.Instant;

/**
 * The Class AuthServiceImpl
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 15/03/2025
 */
@Service
final class AuthServiceImpl implements AuthService {
    private final JwtEncoder jwtEncoder;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(JwtEncoder jwtEncoder, UsuarioRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.usuarioRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponseRecordDTO login(LoginRequestRecordDTO loginRequest) {
        var usuario = usuarioRepository.findByUsername(loginRequest.username());
        if(usuario.isEmpty() || !usuario.get().isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new LoginException("Username or password incorrect");
        }
        var now = Instant.now();

        var claims = JwtClaimsSet.builder()
                .issuer("conversorMoedasAPI")
                .subject(usuario.get().getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(300L))
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new LoginResponseRecordDTO(jwtValue,300L);
    }
}
