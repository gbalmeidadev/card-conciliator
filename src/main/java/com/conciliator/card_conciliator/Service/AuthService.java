package com.conciliator.card_conciliator.Service;

import com.conciliator.card_conciliator.dto.AuthRequestDTO;
import com.conciliator.card_conciliator.dto.AuthResponseDTO;
import com.conciliator.card_conciliator.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponseDTO login(AuthRequestDTO dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.email(),
                        dto.password()
                )
        );

        String token = jwtService.generateToken(dto.email());
        return new AuthResponseDTO(token);
    }
}