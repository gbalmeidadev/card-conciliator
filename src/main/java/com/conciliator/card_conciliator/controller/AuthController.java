package com.conciliator.card_conciliator.controller;

import com.conciliator.card_conciliator.dto.AuthRequestDTO;
import com.conciliator.card_conciliator.dto.AuthResponseDTO;
import com.conciliator.card_conciliator.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody AuthRequestDTO dto) {
        return authService.login(dto);
    }
}