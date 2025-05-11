package com.wefin.conversor_moedas.service;


import com.wefin.conversor_moedas.dto.CreateUserDTO;
import com.wefin.conversor_moedas.model.Usuario;

import java.util.UUID;

/**
* The Interface UserService
*
* @author Miguel Vilela Moraes Ribeiro
* @since 15/03/2025
*/
public interface UsuarioService {
    Usuario createUser(CreateUserDTO createUserDTO);
    Usuario findById(UUID usuarioId);
    boolean existsByUsername(String username);
}
