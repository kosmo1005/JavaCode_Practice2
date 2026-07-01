package com.cool.spring.security.controller;

import com.cool.spring.dto.UserAccountResponseDto;
import com.cool.spring.security.dto.LoginRequestDto;
import com.cool.spring.security.dto.RegisterRequestDto;
import com.cool.spring.security.dto.TokenResponseDto;
import com.cool.spring.security.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Регистрация пользователя
     */
    @PostMapping("/register")
    public ResponseEntity<UserAccountResponseDto> register(@Valid @RequestBody RegisterRequestDto request) {

        UserAccountResponseDto response = authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Аутентификация пользователя
     */
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@Valid @RequestBody LoginRequestDto request) {

        TokenResponseDto response = authService.login(request);

        return ResponseEntity.ok(response);
    }

    /**
     * Обновление access токена
     */
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDto> refresh(@RequestHeader("Authorization") String auth) {

        String token = auth.substring("Bearer ".length()).trim();

        TokenResponseDto response = authService.refresh(token);
        return ResponseEntity.ok(response);
    }
}

