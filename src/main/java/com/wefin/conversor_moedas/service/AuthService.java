package com.wefin.conversor_moedas.service;

import com.wefin.conversor_moedas.dto.LoginRequestRecordDTO;
import com.wefin.conversor_moedas.dto.LoginResponseRecordDTO;

/**
 * The Interface AuthService
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 15/03/2025
 */
public interface AuthService {
    LoginResponseRecordDTO login(LoginRequestRecordDTO loginRequest);
}
